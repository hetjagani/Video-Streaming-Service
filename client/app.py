from flask import Flask, render_template
import requests

app = Flask(__name__)

@app.route("/", methods = ['GET'])
def home():

	response = requests.get("http://10.20.24.51:9090/?path=movies")
	if response.status_code != 200:
		response = requests.get("http://10.20.24.14:9090/?path=movies")

	movies = response.json()
	
	# movies = [
	# 	{
	# 		'id': 1,
	# 		'name': 'Inception',
	# 		'genre': 'Suspense',
	# 		'rating': 9.1,
	# 		'release_year': 2010,
	# 		'description': 'Great Movie'
	# 	},
	# 	{
	# 		'id': 2,
	# 		'name': 'Shutter Island',
	# 		'genre': 'Suspense',
	# 		'rating': 9.3,
	# 		'release_year': 2010,
	# 		'description': "In 1954, up-and-coming U.S. marshal Teddy Daniels is assigned to investigate the disappearance of a patient from Boston's Shutter Island Ashecliffe Hospital. He's been pushing for an assignment on the island for personal reasons, but before long he thinks he's been brought there as part of a twisted plot by hospital doctors whose radical treatments range from unethical to illegal to downright sinister. Teddy's shrewd investigating skills soon provide a promising lead, but the hospital refuses him access to records he suspects would break the case wide open. As a hurricane cuts off communication with the mainland, more dangerous criminals \"escape\" in the confusion, and the puzzling, improbable clues multiply, Teddy begins to doubt everything - his memory, his partner, even his own sanity."
	# 	},
	# 	{
	# 		'id': 1,
	# 		'name': 'Inception',
	# 		'genre': 'Suspense',
	# 		'rating': 9.1,
	# 		'release_year': 2010,
	# 		'description': 'Great Movie'
	# 	},
	# 	{
	# 		'id': 1,
	# 		'name': 'Inception',
	# 		'genre': 'Suspense',
	# 		'rating': 9.1,
	# 		'release_year': 2010,
	# 		'description': 'Great Movie'
	# 	},
	# ]
	return render_template("index.html", movies=movies)

@app.route("/detail/<int:id>", methods=['GET'])
def movie_detail(id):

	response = requests.get("http://10.20.24.51:9090/?path=movies/{}".format(id))
	if response.status_code != 200:
		response = requests.get("http://10.20.24.14:9090/?path=movies/{}".format(id))

	movie = response.json()

	# movie = {
	# 		'id': 2,
	# 		'name': 'Shutter Island',
	# 		'genre': 'Suspense',
	# 		'rating': 9.3,
	# 		'release_year': 2010,
	# 		'duration': 120,
	# 		'description': "In 1954, up-and-coming U.S. marshal Teddy Daniels is assigned to investigate the disappearance of a patient from Boston's Shutter Island Ashecliffe Hospital. He's been pushing for an assignment on the island for personal reasons, but before long he thinks he's been brought there as part of a twisted plot by hospital doctors whose radical treatments range from unethical to illegal to downright sinister. Teddy's shrewd investigating skills soon provide a promising lead, but the hospital refuses him access to records he suspects would break the case wide open. As a hurricane cuts off communication with the mainland, more dangerous criminals \"escape\" in the confusion, and the puzzling, improbable clues multiply, Teddy begins to doubt everything - his memory, his partner, even his own sanity."
	# 	}

	return render_template("movie_detail.html", movie=movie)

if __name__ == "__main__":
	app.run(host='0.0.0.0', port=5050)

"""
Movie Details: 

	1) name
	2) genre
	3) duration
	4) rating
	5) release_year
	6) total_frames
	7) fps
	8) description
	9) filepath
	10) height
	11) width
	12) format
	13) filesize

"""
