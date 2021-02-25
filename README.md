# TradesyListYelpTest

# Features implemented
- Location permission check.
- Yelp search at current location or selected city if location permission is denied.
- Listed businesses with name and image plus star rating and review.
- Rotation of list.

# Use-cases
- StartApp, select Deny location permission, select NYC -> OKAY, search, observe list of NYC businesses.
- StartApp, select Deny location permission, dismiss next dialog, search, observe default list of location businesses.
- StartApp, select Deny location permission, select NO..., search, observe list of local businesses.
- StartApp, allow location permission, search, observe "Showing results for " current search text and list of local businesses.
- Redo same search, observe same local businesses.
- With list, scoll quickly while loading, obsever text and pictures loading without effecting scroll.
- With list, rotate to landscape, repeat previous use-case.
- With list, select business, observe name toast.

# Showcasing
- MVI pattern.
- Use of mappers to handle room, repo, and cloud.
- Use of hilt's DI, viewModel and coroutines.
- Refactoring of common code into utilities.
- Useful and thorough documentation of the code.

# Dependencies highlights
 - Kotlin Ext
 - Kotlin Coroutines
 - Glide
 - Retrofit2
 - Room
 - Lifecycle
 - Okhttp3:logging
 - Google play-services-location
 - Google dagger:hilt
 
# Tasks undone
 - Interface for preferences to generically handle preferences.
 - Actual navigation to a detail page where toast is displayed.
 - Use AndroidJUnitRunner and launchFragmentInHiltContainer for a injection-based (using @HiltAndroidTest) search test.
 - Animation.
 - Add material Design themes and make the app look better.
 - Landing page and cool launch icon.
 - Retain search text for next app launch.
 
 # Challenge
 - Too much to do in the allotted time.
