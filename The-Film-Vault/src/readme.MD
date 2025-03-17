Planning Deliverables:

1. Use Cases:
   User Registration- allow users to register an account
   User visits the registration page and submits a form with an email, username, and password.
   Exceptions:
   Registration fails if the username or email is already taken.

   Log In - Allow a user to log in to their account.
   User navigates to the Login Page.
   User enters email/username and password.
   User clicks "Login".
   System verifies credentials:
   If correct, a session is created.
   If incorrect, an error message is displayed.
   If successful, user is redirected to the homepage.


    browse/Movie Search - User navigates to the movie list page.
                    User navigates to the Homepage.
                    The system displays a list of movies.
                    User enters a search query (title, genre, or release year).
                    System filters and returns results.
                    User clicks on a movie to view details.

    Movie Details - User clicks a movie from the Homepage.
                System retrieves movie details (title, genre, director, rating).
                System displays:
                Movie Information
                User Reviews
                "Add to Watchlist" button (for logged-in users).

    Movie Review - User navigates to a movie’s page.
                    User writes a review and assigns a rating (1-5).
                    User submits the review.
                    System stores the review and updates the average rating.

    WatchList - User clicks "Add to Watchlist".
                                System adds the movie to their watchlist.
     
     Already Watched -  User later clicks "Mark as Watched". System moves the movie to the watched list.

     Chat feature (stretch goal) 

Outside Technology Ideas-
1. Websocket (will attempt if time wills it) (allow users to discuss movies live while browsing enabling real time chat)
2. Google Auth Login - Allow users to sign in with Google instead of email/password.
3. Deploy to AWS
4. Tailwind css framework

Models -

User - 	Represents registered users (id, username, email, password, role).
Movie - 	Represents movies (id, title, genre, release year, director, rating).
Review -	Represents user reviews (id, user, movie, rating, comment, timestamp).
UserMovieStatus -	Tracks Watchlist & Watched Movies (user_id, movie_id, status).
Genre -	Stores movie genres (id, genre_name).


Service -

UserService-	Handles user-related logic (register, login, session management).
MovieService-	Processes movie-related operations (fetch, search, add/edit movies).
ReviewService -	Processes user reviews, make sure only one review per user per movie.
UserMovieStatusService -	Manages Watchlist & Watched Movies (add/remove movies from lists).

Repositories-

Controllers -




Frontend components:

App.js
Navbar.js

Movie related:
homepage.js
movielist.js
moviecard.js
moviedetails.js


User Account:
userprofile.js
watchlist.js
reviewhistory.js

