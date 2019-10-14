package com.cloud.streaming;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

@Path("/")
public class StreamingRestService extends ResourceConfig {

	final static Logger logger = Logger.getLogger( StreamingRestService.class );
	
	private static final String FILE_PATH = "/video1.mp4";
    private final int chunk_size = 1024 * 1024 * 2; // 2 MB chunks
    
    public StreamingRestService() {
		packages("com.cloud.streaming");
	}
    
    // for clients to check whether the server supports range / partial content requests
    @HEAD
    @Path("/stream")
    @Produces("video/mp4")
    public Response header() {
    	logger.info("@HEAD request received");
    	
        URL url = this.getClass().getResource(FILE_PATH);
        File file = new File( url.getFile() );
        
        return Response.ok()
        		.status( Response.Status.PARTIAL_CONTENT )
        		.header( HttpHeaders.CONTENT_LENGTH, file.length() )
        		.header( "Accept-Ranges", "bytes" )
        		.build();
    }

    @GET
    @Path("/stream")
    @Produces("video/mp4")
    public Response stream( @HeaderParam("Range") String range ) throws Exception {
    	
        URL url = this.getClass().getResource( FILE_PATH );
        File file = new File( url.getFile() );
        
        return buildStream( file, range );
    }

    /**
     * @param asset Media file
     * @param range range header
     * @return Streaming output
     * @throws Exception IOException if an error occurs in streaming.
     */
    private Response buildStream( final File asset, final String range ) throws Exception {
        // range not requested: firefox does not send range headers
        if ( range == null ) {
        	logger.info("Request does not contain a range parameter!");
        	
            StreamingOutput streamer = new StreamingOutput() {
				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException {
				    try ( FileChannel inputChannel = new FileInputStream( asset ).getChannel(); 
				    	  WritableByteChannel outputChannel = Channels.newChannel( output ) ) {
				    	
				        inputChannel.transferTo( 0, inputChannel.size(), outputChannel );
				    }
				    catch( IOException io ) {
				    	logger.info( io.getMessage() );
				    }
				}
			};
            
            return Response.ok( streamer )
            		.status( Response.Status.OK )
            		.header( HttpHeaders.CONTENT_LENGTH, asset.length() )
            		.build();
        }

        logger.info( "Requested Range: " + range );
        
        String[] ranges = range.split( "=" )[1].split( "-" );
        
        int from = Integer.parseInt( ranges[0] );
        
        // Chunk media if the range upper bound is unspecified
        int to = chunk_size + from;
        
        if ( to >= asset.length() ) {
            to = (int) ( asset.length() - 1 );
        }
        
        // uncomment to let the client decide the upper bound
        // we want to send 2 MB chunks all the time
        if ( ranges.length == 2 ) {
            to = Integer.parseInt( ranges[1] );
        }
        
        final String responseRange = String.format( "bytes %d-%d/%d", from, to, asset.length() );
        
        logger.info( "Response Content-Range: " + responseRange + "\n");
        
        final RandomAccessFile raf = new RandomAccessFile( asset, "r" );
        raf.seek( from );

        final int len = to - from + 1;
        final MediaStreamer mediaStreamer = new MediaStreamer( len, raf );

        return Response.ok( mediaStreamer )
                .status( Response.Status.PARTIAL_CONTENT )
                .header( "Accept-Ranges", "bytes" )
                .header( "Content-Range", responseRange )
                .header( HttpHeaders.CONTENT_LENGTH, mediaStreamer. getLenth() )
                .header( HttpHeaders.LAST_MODIFIED, new Date( asset.lastModified() ) )
                .build();
    }

}
