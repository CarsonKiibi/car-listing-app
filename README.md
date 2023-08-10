# My Personal Project

**Car market application**. The application will allow a user to view a collection of cars for sale,
and list their own car as well. Each car for sale will have details that the user must enter when
listing, such as make, model, year, colour, mileage, etc. Their will be a page to view the listings,
and the user should be able to click a listing to view more details. In theory, someone wishing to
buy or sell a car would use this application. This project is of interest to me since I feel I have
decent knowledge about cars and enjoy learning about them.

Some extra features that I would like to include but may decide not to depending on time/difficulty
would be the ability to edit listings, add pictures (or select from stock photos maybe),
randomly generating listings to simulate the experience better, allow the user to have an inventory
of purchased, currently listed, or sold cars. Or perhaps a history of those interactions. These
features may overstep the requirements of the project so it is purely based on time and complexity.

## User Stories

- As a user, I want to be able to add a detailed car listing to a list of car listings within the application
- As a user, I want the application to allow me to add my cars make, model, year and mileage to a listing
- As a user, I want to view all other listings which may include my own
- As a user, I want to be able to run the application, and have options to save, load, add listing, view listings, exit
- As a user, I want to be able to save the current state of the application (existing car listings)
- As a user, I want to be able to re-open the saved state of the application
- As a user, I want to be able to remove a listing from the list of listings
- As a user, I want to be able to filter listings by settings a minimum and maximum price

## Phase 4, Task 2

- User opens app
- User adds a listing with low price
- User views listings
- User adds a listing with high price
- User views listings
- User applies filter bounds to filter out low price listing
- User views listings, only 1 listing on view listing page
- User closes app

LOG:

Wed Aug 09 21:57:07 PDT 2023
Added car listing to list of listings.

Wed Aug 09 21:57:09 PDT 2023
Looped car listings, displayed a listing.

Wed Aug 09 21:57:09 PDT 2023
All listings were in price range.

Wed Aug 09 21:57:26 PDT 2023
Added car listing to list of listings.

Wed Aug 09 21:57:28 PDT 2023
Looped car listings, displayed a listing.

Wed Aug 09 21:57:28 PDT 2023
Looped car listings, displayed a listing.

Wed Aug 09 21:57:28 PDT 2023
All listings were in price range.

Wed Aug 09 21:57:33 PDT 2023
Looped car listings, displayed a listing.

Wed Aug 09 21:57:33 PDT 2023
1 listings were in price range.

## Phase 4, Task 3

A very important change I would make would be splitting the GUI up into more
classes in the UI folder. Right now it is difficult to navigate or change
much of what it is actually doing on the UML diagram. Examples would be a separate
file for the menu, addListings, view listings at the very least. With that addition,
a user could gather more of what the app actually does just from the UML diagram.
I would also like to make a super class listing() and then have carListing, truckListing,
etc so the user could have more options for types of listings. This could even give room
to make a craigslist type app where you could post literally anything.