# TripTrip-MOBDEVE

### Features implemented so far:
- Database for user login and account registration
- Connecting of activities except for edit trip and view trip details
- Connecting of menu to activity containing list of planned trips (activity_trips.xml)

#### At this point, the user is able to:
- Log into the app
- Access and view the forgot password activity, then access the create password activity
- Register an account
- See the RecyclerView list of planned trips (temporarily filled with hardcoded dummy data for now)
- Access and see the add trip activity
- Access the app menu and its options
- See the view profile activity, and then access the edit profile activity
- See the edit profile activity
- Log out of the app

### Views that have been added with layout and design: 
- [activity_add_trip.xml] activity view for adding a new trip
- [activity_edit_profile.xml] activity view for edit profile
- [activity_edit_trip.xml] activity view for editing a planned trip
- [activity_forgot_password.xml] activity view for forgot password verification
- [activity_login.xml] activity view for user login
- [activity_main.xml] activity view for main view
- [activity_new_password.xml] activity view for creating new password after forgot password verification
- [activity_register.xml] activity view for register account 
- [activity_trips.xml] activity view for RecyclerView list of planned trips
- [activity_view_profile.xml] activity view for view profile details
- [item_dropdown.xml] view for dropdown menus used in the app
- [item_trip.xml] view for a single trip item 
- [menu_settings.xml] view for user menu

### Java classes that have been modified so far:
- [AddTripActivity.java] Java class for add trip activity
- [DataBaseHelper.java] Java class for database implementation
- [DataHelper.java] temporarily added Java class for Reyclerview testing
- [EditProfileActivity.java] Java class for edit profile activity
- [EditTripActivity.java] Java class for edit trip activity
- [ForgotPasswordActivity.java] Java class for forgot password activity
- [LoginActivity.java] Java class for login activity
- [MainActivity.java] Java class for main activity
- [NewPasswordActivity.java] Java class for create new password activity
- [RegisterActivity.java] Java class for register account activity
- [Trip.java] Java class for trip item
- [TripAdapter.java] Java class for trips adapter
- [TripsActivity.java] Java class for list of planned trips activity
- [TripViewHolder.java] Java class for trips viewHolder
- [User.java] Java class for a single user
- [ViewProfileActivity.java] Java class for view profile details activity

### Features that are yet to be implemented:
- Retrieve user profile details and display them on view profile activity
- Google Maps API for adding and editing a trip
- Camera API for adding an image to a planned trip
- Adding a trip to the database and activities
- Removing a trip from the database and activities
- Changing trip details in the database according to inputs from the edit trip activity
- Changing user details in the database according to inputs from the edit profile activity
- Retrieve trip details from the database and display trip title, start and end dates, start and end location on a trip item in the RecyclerView list of trips
- Retrieve trip details from the database and display all on the view trip details activity
- App logo and other additional aesthetic elements
