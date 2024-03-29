Original App Design Project
===

# HANGOUTS (not final)

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description

Hangouts is an event/social planner app that allows users to create events or "Hangouts" and share them with other users. Then, said users can join the event and rank their top picks for meal selection. The app then will run an algorithm to process each members preferences and calculate which restaurants might be a good fit for te member's current cravings. Finally the app will display those restaurants on a map so participants can make a decision on where to go.

### App Evaluation

- **Category:** Social/Planning
- **Mobile:** Mobile android application, reads and writes data.
- **Story:** Allow users to plan a hangout together. One user will create an event/hangout and will send either a code or a link for other users to join. There will be different kinds of hangouts (entretainment, meals, nightactivity, etc.) and depending on the type, each joining user will complete a tinder like sequence to gather data on their preferences. The app will then suggest to the group places to go taking into account the members preferences.
- **Habit:** Users can create a new hangout and will be able to share the link. Users will be able to singup with facebook and post in facebook timeline for friends to join the event. Users can see previous hangout history and hangout members.
- **Scope:** MVP: Users can sign-up, create a hangout, share it and join hangouts. The app will make basic recommendations based on user data and display a map view with places to go to. V2: Better recommendations, better ui, Facebook signup.
- **Complexity Aspects:** Algorithm for suggestions: Input -> user preferences on one category (give categories weight)(e.g Cousine: Italian, Chinese, Japanese, Mexican etc. Type of food: Non-vegan, vegan, vegetarian, non-vegetarian) -> [algorithm] -> Output -> a list of restaurants that will satisfy the group's preference on a certain level and display them on a map.

## Demo

### Signup / Onboarding

<p align="center">
<img src="https://github.com/manuelrurda/Hangouts/blob/main/demogifs/Signup_Onboarding.gif" alt="Signup and Onboarding" width="200"/>
</p>

### Create Hangout

<p align="center">
<img src="https://github.com/manuelrurda/Hangouts/blob/main/demogifs/CreateHangout.gif" alt="Create Hangout" width="200"/>
</p>

### Join Hangout

<p align="center">
<img src="https://github.com/manuelrurda/Hangouts/blob/main/demogifs/JoinHangout.gif" alt="Join Hangout" width="200"/>
</p>

### Close Hangout and Results

<p align="center">
<img src="https://github.com/manuelrurda/Hangouts/blob/main/demogifs/CloseHangout1.gif" alt="Close Hangout" width="200"/>
</p>

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users can signup using Parse.
* Users can start a hangout/event.
* Users can set hangout properties like: type of hangout, location, location range (max distance).
* Users can share a link for other users to join the event.
* User data will be collected in a Tinder swipe style sequence.
* The app will provide recommendations based the event member's preferences.

**Optional Nice-to-have Stories**

* Polished UI
* Different Signup options (Twitter, Facebook, Google)
* Advanced recommendation algorithm
* Better authentication.

### 2. Screen Archetypes

* Log in Screen
  * Users can log in or signup
  * Text fields to enter username and password
  * Button to sign up screen if user doesn't have an account.
  * SVG graphic for more polished ui

* Signup
  * Signup using Parse
  * Text fields for name, last name, username and password
  * Button to signup and return to login
  * Null detection for all fields.

* Onboarding (preference capture)
  * Tinder like sequence
  * Swipe cards
  * Card must have a picture and title
  * Buttons to like or dislike for automatic swipe

* Dashboard (home screen)
  * See current open events
  * See past events
  * Buttons to create or join event

* Event Creation screen
  * Ask for event name/alias
  * Button to select location
  * Set distance radius (maybe stretch)
  * Date and Time for deadline
  * Button to start and create event

* Location selections screen (maps -> Uber like)
  * Map screen
  * Type address
  * Set pin on map

* Event Detail screen
  * Opens when event is tapped in home screen

* Ranking screen - after joining event
  * Ask for users to rank current cravings for algorithm input
  * Drag and Drop to rank

* Final results screen - map with restaurants listed Decision/voting screen (stretch)


### 3. Navigation

**Flow Navigation** (Screen to Screen)

* Login Screen
  * Login Button -> Onboarding Screen if first time login -> Home Screen
  * Signup Button -> Signup Screen -> Back to Login Screen
* Onboarding Screen
  * Gather preferences -> Home Screen
* Home Screen
  * Create Button -> Event Creation Screen
  * Join Button -> Code Input Screen
  * Tap on current/past event -> Event Details Screen
* Event Creation Screen
  * Select Location Button -> Map Screen -> Back to Event Creation Screen
  * Create Button -> Code Generation -> Home Screen, new event added.
* Code Input Screen
  * Enter event code -> Back to Home Screen, new event added.
* Event Details Screen
  * Map with recommendations.

## Wireframes

<img src="https://i.imgur.com/zgRksuo.jpg" width=400>

### [BONUS] Digital Wireframes & Mockups (Work in Progress)

<img src="https://i.imgur.com/cSUGiDH.png" width=400>

## Schema

#### User
| **Property**      | **Type**       | **Description**                                                      |
|-------------------|----------------|----------------------------------------------------------------------|
| objectId          | String         | Unique string to identify the object within the database             |
| name              | String         | User's name                                                          |
| lastName          | String         | User's last name                                                     |
| username          | String         | User's unique username for authentication                            |
| password          | String         | User's password for authentication                                   |
| cuisinePreferences | Array<String>  | Array to store user's cuisine type  preferences                       |
| activeHangouts    | Array<Hangout> | Array to store currently active Hangouts  the user is taking part of |
| pastHangouts      | Array<Hangout> | Array to store Hangouts  the user has taken part of in the past      |

#### Hangout
| **Property**      | **Type**          | **Description**                                                                 |
|-------------------|-------------------|---------------------------------------------------------------------------------|
| objectId          | String            | Unique string to identify the object within the database                        |
| alias             | String            | Alias for users to easily identify Hangouts                                     |
| members           | Array<User>       | Array to store the Hangout participants                                         |
| locationLatitude  | Double            | Latitude coordinate for event location                                          |
| locationLongitude | Double            | Longitude coordinate for event location                                         |
| deadline          | Date              | Deadline for users to join the event                                            |
| recommendations   | Array<JSONObject> | Array of API responses containing the output from the recommendations algorithm |

### Models

<img src="https://i.imgur.com/WJCihTT.png" width=400>

### Networking
- Signup Screen
  - Parse call to signup user
- Login Screen
  - Parse call to start user session
- Location Selection
  - Google Maps API to select location or place pin on map
- Event details Screen
  - Google Places call to fetch restaurants depending on suggestions output.
- [Create basic snippets for each Parse network request]

### Dependencies and API's

* Glide: image loading. https://bumptech.github.io/glide/
* Parse: Database. https://www.back4app.com/docs/get-started/welcome
* SwipeCards: Third party card view with swiping capabilities. https://github.com/Diolor/Swipecards
* Google Maps API: Google service to display location on map. https://developers.google.com/maps/documentation/android-sdk
* Google Places API: Google service to fetch data from millions of businesses. https://developers.google.com/maps/documentation/places/android-sdk

## Weekly Plan

Plan:

#### W1
- [x] sign-in/signout
- [x] Parse setup/model setup

#### W2
- [x] Onboarding
- [x] Drag and Drop UI
- [x] Dashboard

#### W3
- [x] Event creation
- [x] Location selection

#### W4
- [x] Ranking screen
- [x] Event Detail screen

#### W5
- [x] Suggestions algo
- [x] Final results screen

#### W6 - Stretch Goals
- [ ] Store prev events
- [ ] Offline capabilities(stretch goal)
- [ ] decision/voting screen



![thumbnail](https://github.com/manuelrurda/Hangouts/assets/62727899/c7dc473d-6f19-415d-845f-99e792445142)
