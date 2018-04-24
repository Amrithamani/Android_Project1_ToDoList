# Android_Project1_ToDoList

This project implemented the following concepts
• activity
• intent
• menu
• dialogs
• database

Home screen displays the List View having

TextView 1: For the Title of the To-Do Task.
TextView 2: Description of the To-Do Task.
TextView 3: Timestamp or due date of the To-Do task.

The entire list item sorted in ascending order based on the due date of
each task.

Menu item is created and the item has is placed on the
ActionBar.

Upon click on the Add item (plus icon) a dialog pops-up asking for the details
from the user.

Dialog contains two EditTexts, a DatePicker and two Buttons.

EditTexts  have a minimum characters entered (Validation is there).
Upon click on save button the details are saved to the database.

Database contains 
KEY_ID ---> INTEGER PRIMARY KEY
KEY_TITLE ---> TEXT
KEY_DESCRIPTION ---> TEXT
KEY_DATE ----> TEXT

Upon clicking on the list item, an AlertDialog box should be popped up with Update and Delete.

If the changes are made to the list item and the same is reflected in the ListView
with the database.
