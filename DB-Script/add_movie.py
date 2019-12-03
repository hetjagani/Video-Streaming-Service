import cv2
import sqlite3 as lite
import os
import json
from shutil import copyfile, rmtree
import sys
import time

monitor_dir_path = "/home/ubuntu-1/Cloud_project/monitor"
shared_vol_path = "/home/ubuntu-1/dist"
db_path = os.path.join(shared_vol_path, "databases")

while True:
    for folder in os.listdir(monitor_dir_path):
        movie_dict = {"name":"", "genre": "", "duration": 0, "description": "", "rating": 0, "release_year": "",
                        "total_frames": 0, "fps": 0., "filepath": "", "height": 0, "width": 0, "filesize": 0,
                        "format": ""}
        check = 0
        movie_folder = os.path.join(monitor_dir_path, folder)
        try:
            for fn in os.listdir(movie_folder):
                if fn.endswith(".mp4"):
                    orig_vid_path = os.path.join(movie_folder, fn)
                    cap = cv2.VideoCapture(os.path.join(movie_folder, fn))
                    movie_dict["total_frames"] = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
                    movie_dict["fps"] = cap.get(cv2.CAP_PROP_FPS)
                    movie_dict["duration"] = round((movie_dict["total_frames"]/movie_dict["fps"])/60)
                    movie_dict["filesize"] = os.stat(os.path.join(movie_folder, fn)).st_size/(1024*1024)
                    movie_dict["height"] = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
                    movie_dict["width"] = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
                    movie_dict["format"] = fn.split(".")[-1]
                    check += 1
                elif fn.endswith(".json"):
                    with open(os.path.join(movie_folder, fn)) as f:
                        about_movie_dict = json.load(f)
                    movie_dict["name"] = about_movie_dict["name"]
                    movie_dict["genre"] = about_movie_dict["genre"]
                    movie_dict["description"] = about_movie_dict["description"]
                    movie_dict["rating"] = float(about_movie_dict["rating"])
                    movie_dict["release_year"] = str(about_movie_dict["release_year"])
                    check += 1
                else:
                    pass
        except Exception as e:
            print("Exception occured while extracting information : ")
            print(e)
            continue
        if check == 2:
            fpath = "/media/" + movie_dict["name"].replace(" ", "_") + "." + movie_dict["format"]
            movie_dict["filepath"] = fpath
            dest_vid_path = shared_vol_path + fpath
            print(orig_vid_path)
            print(dest_vid_path)
            copyfile(orig_vid_path, dest_vid_path)
            for key, val in movie_dict.items():
                print(key, val, type(val))
            try:
                print(os.path.join(db_path, "project"))
                con = lite.connect(os.path.join(db_path, "project"))
                cur = con.cursor()
                query = "INSERT INTO movies VALUES(NULL, '{}', '{}', {}, {:.1f}, {}, {}, {:.2f}, '{}', '{}', {}, {}, '{}', {:.2f})".format(movie_dict["name"], 
                    movie_dict["genre"],
                    movie_dict["duration"],
                    movie_dict["rating"],
                    movie_dict["release_year"],
                    movie_dict["total_frames"],
                    movie_dict["fps"],
                    movie_dict["description"],
                    movie_dict["filepath"],
                    movie_dict["height"],
                    movie_dict["width"],
                    movie_dict["format"],
                    movie_dict["filesize"])
                print(query)
                cur.execute(query)
                con.commit()
            except Exception as e:
                print("Exception occured while performing operations in db : ")
                print(e)
                sys.exit(1)
            finally:
                if con:
                    con.close()
    for content in os.listdir(monitor_dir_path):
        file_path = os.path.join(monitor_dir_path, content)
        try:
            if os.path.isfile(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                rmtree(file_path)
        except Exception as e:
            print("Exception occured while deleting contents of monitoring directory : ")
            print(e)
    time.sleep(10)
