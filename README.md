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



## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users can signup using Parse.
* Users can start a hangout/event.
* Users can set hangout properties like: type of hangout, location, location range (max distance).
* Users can share a link for other users to join the event.
* User data will be collected in a Tinder swipe style sequence.
* The app will provide recomendations based the event member's preferences.

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

* [list second screen here]
    * [list associated required story here]
    * ...

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [fill out your first tab]
* [fill out your second tab]
* [fill out your third tab]

**Flow Navigation** (Screen to Screen)

* [list first screen here]
    * [list screen navigation here]
    * ...
* [list second screen here]
    * [list screen navigation here]
    * ...

## Wireframes

<img src="https://i.imgur.com/zgRksuo.jpg" width=400>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]