# ucsb-cs56-ride2school
Facebook Rideshare

A rideshare group app.

website: https://ride2school.herokuapp.com/

MVP
Web interface that displays feed of all ride postings
User can post listings

User Stories
User can access the website via URL
User is shown a feed of all the posts
User can scroll through a listing of posts 

# Set Up Information

For this project to work properly (or run at all), there must be proper setup with MongoDB, Mlab, and a env.sh file. Here are the steps to achieving this.

__Part One create an MLAB account:__

- Create a free mLab account at [mLab.com](https://mlab.com)
- Create a new deployment with any hosting service
- Click on your new deployment
- Go the users tab and make a new user
- Take note of the user credentials your just made, your db name, and host

```
mongodb://<dbuser>:<dbpassword>@d<dbhost>/<dbname>
```

__Part Two set up env.sh file:__

- Run the following command in the root directory of the project:

```
touch env.sh
```

- This will create a file with the name env.sh that we will put our enviroment variables in.
- Edit the file and put the following information in:

```
export USER_=<your user's username>
export PASS_=<your user's password>
export DB_NAME_=<your db name>
export HOST_=<your host name>
```

- Now run the command to allow the program to see the information:

```
. env.sh
```

__!!! IMPORTANT !!!__
You must run the following command in terminal every session. Without this there will be an error!

__Using Heroku (optional, currently not working fix that for 100 pts)__

If you are using heroku follow these steps:

1. Go to your dashboard ```https://dashboard.heroku.com/apps/<project-name>/settings```
2. Click on __Settings__
3. Click __Reveal Config Vars__ inside the __Config Vars__ menu
5. Input your key value pairs for your config variables in the same way as the environment variables

```
USER_        <your user's username>
PASS_        <your user's password>
DB_NAME_     <your db name>
HOST_        <your host name>
````

__Final Notes__

Now your program should be up and running! If you did anything in this step wrong, there should be an error when running the project with debug information. Have fun and catch those rides!
