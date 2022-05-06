# Storytime Reading Tracker
_This application was developed and presented in the final 2 weeks of my time at Tech Elevator_

A live version of this application is available [here.](https://storytime-reading-tracker.netlify.app/ "Storytime App")

## Description

   - Storytime is meant for use with families interested in helping foster a love of reading and building good habits. 
   - It was completed in a group of 4 students working with a Scrum Master, who maintained and monitored development pace, and Product Owner, who outlined necessary features.
   - The team practiced agile principles, developing for user stories in several sprints over the course of the project, and with code review afterwards.

## Technology Used

Vue.js, Java, Spring, PostgreSQL

## Feature List 

### Frontend
   #### Login/Register
   Allows parents to register their families and login for existing users, parent or child
   ![image](https://user-images.githubusercontent.com/85130370/165379844-53116edf-bb2a-4879-b0cc-04793a1cdd02.png)   
   ![image](https://user-images.githubusercontent.com/85130370/165380001-b4c0a7c4-7b40-419b-a97d-4a608c70608c.png)
   #### Dashboard
   Allows parents see basic statistics of all family members, log reading of themselves or children, add a book for family members to read from, and register new family members.
   ![image](https://user-images.githubusercontent.com/85130370/165381241-81859c8c-00a8-42f8-9f02-63bd539ff373.png)
   Includes user modal for detailed view of a single family member.
   ![image](https://user-images.githubusercontent.com/85130370/165381549-49ce19f0-dc51-401e-b3d0-7e3951370c25.png)
   #### Family Bookshelf
   Up-to-date display of all books available for the current user's family to read. Searches for cover images based on given ISBN using [Open Library](https://openlibrary.org/ "Open Library"). Includes buttons for sorting books by various fields.
   ![image](https://user-images.githubusercontent.com/85130370/165382399-3bb8a482-e3c1-47d9-ad79-64c960d28765.png)
   #### Reading Log 
   Shows all reading of family members from newest to oldest activity.
   ![image](https://user-images.githubusercontent.com/85130370/165382536-fc15eb6c-0b8c-4f8c-8422-5b25cf46dcc5.png)
    
### Backend
  #### Authentication
    - POST /login - log in as a user 
    - POST /register - create a new user account 
    - POST /parent/dashboard/:id - create a new user within the current user's family 
    - GET /parent/dashboard/:id - view all users in the current user's family
    - GET /user/dashboard/:id - view dashboard information for all users in family
  #### Books
    - GET /book/:id - return a book when given its ID
    - POST /bookshelf/:id - creates a book in the family library
    - GET /bookshelf - returns all of a user's family's books
    - POST /readinglog - creates a reading log entry
    - GET /readinglog - returns a single reading log post 
    - GET /readinglog/books/:id - returns all family reading log posts from a user ID
    - GET /user/dashboard/detail/:id - returns all reading activity of a single family member 
    - GET /readinglog/:id - returns reading log entries of a family member based on ID

## Acknowledgements
  A big thank you to my team members, [Colin Davis](https://www.linkedin.com/in/colin-randolph-davis/ "Colin Davis"), [Kai Indigo Wolf](https://www.linkedin.com/in/indigowolf/ "Kai Indigo Wolf"), and [Reginald Arnedo](https://www.linkedin.com/in/reginald-arnedo/ "Reginald Arnedo"), and our Scrum Master for the project [Mary Mosman](https://www.linkedin.com/in/mary-mosman/ "Mary Mosman").
