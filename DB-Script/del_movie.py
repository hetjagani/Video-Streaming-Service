import sqlite3 as lite
import os
from shutil import copyfile, rmtree
import sys

vid_path = "/home/ubuntu-1/dist/media/"
db_path = "/home/ubuntu-1/dist/databases/project"

filename = ""
movielist = []


try:
    con = lite.connect(db_path)
    cur = con.cursor()
    query = "SELECT id, name, format from movies"
    cur.execute(query)
    rows = cur.fetchall()
    for row in rows:
        movielist.append(row)    
        print(row)

    movie_id = input("Enter id of the movies : ")

    for ent in movielist:
        if ent[0] == int(movie_id):
            filename = ent[1].replace(" ", "_") + "." + ent[2] 
            break

    print("deleting the movie...")
    query = "DELETE FROM movies WHERE id=" + movie_id + ";"
    cur.execute(query)
    con.commit()
    os.unlink(vid_path + filename)

except Exception as e:
    print("Exception occured while performing operations in db : ")
    print(e)
    sys.exit(1)
finally:
    if con:
        con.close()