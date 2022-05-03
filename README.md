# SimpleProfileView
This is a project for a code challenge.

### Instructions: 
Write an app that manages a list of users. Each user has:
- A name
- An avatar image 
- A bio


The app should have two screens. The first is a list of all users registered in the app. You should be able to add new users to the list from this page, as well as remove them. Tapping on a user in the list should take you to a "profile page" for the user, which will display their attributes. All attributes should be editable from the profile page, and you should be able to choose an image to set the user's avatar. The list of users does not need to persist between app launches, but it should persist as long as the app is running.

### Solution:
- The proposed app allows the user to add new records. If the list is empty it displays a message. As each record is entered with image, name and description, it is stored locally and is persistent even if the app is closed.
- The architecture used is MVVM.
- Hilt was used as DI library.
- Room was used as the database library.
- Glide was used as a library for image loading.
- In addition, unit test coverage was performed using MockK as a Mocking tool for the UseCases of the ViewModels.
- For the UI tests we used the Robot pattern suggested by Jake Wharton, and espresso was used as the base library.

![Captura de Pantalla 2022-05-03 a la(s) 16 32 23](https://user-images.githubusercontent.com/20761077/166555945-76abba5a-8d19-4b6c-a96c-5be26341a3d8.png)



### Demo
|Create user|
|---|
|<img width="320" alt="Create user" src="https://user-images.githubusercontent.com/20761077/166555994-5b5358c3-7920-4cbb-9d57-3f83377b6928.gif">|

|Edit user|
|---|
|<img width="320" alt="Edit user" src="https://user-images.githubusercontent.com/20761077/166556012-84610a67-463b-4319-9a5f-abe06a379e95.gif">|

|Remove user|
|---|
|<img width="320" alt="Remove user" src="https://user-images.githubusercontent.com/20761077/166556028-6e3d4270-ae7f-46c2-b945-d1ee442cbc79.gif">|

|Error handling|
|---|
|<img width="320" alt="Error case" src="https://user-images.githubusercontent.com/20761077/166556054-d257c227-bee5-4741-8f88-faf88735f91e.gif">|
