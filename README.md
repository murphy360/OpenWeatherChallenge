# OpenWeatherChallenge

This application is developed for an Interview Process.  Below are issues that I've identified thus far. 

Targeting Multiple Screen Sizes:
- More work needs done here. I’ve shown basics on how to utilize multiple versions of layouts (main activity). 
- Would rely heavily on dimens.xml to provide values for multiple screens

Icons: 
- Icons provided did not align with API returns. 
- I didn't have time to download and modify all icons to align with the data I was receiving so I added a feature that would download the icons from the website in real time.
- These icons are smaller and appear stretched especially on larger screens. 
- I would request / build new / complete set of icons moving forward. 

Known  Issues: 
- 5 day forecast seems to be returning Today as well. 
- Requested 6 days (modified provided URL) and disregard first entry in the list. 

Temperature
- For Today I use Current Temp and Min temp (Details view / Today View)
- For Days in the Future I use Max and Min temps (Details View / List View)

Wind Direction
- NNE is 337.5 Clockwise to 22.5degrees I'm using integers and thus don't have decimals for now I'm assuming that N will be > 338 clockwise to 23 degrees

Views / Material Design
- Example of elevation in Main Activity... Need to apply throughout.
- I have a lot of things (Strings etc) hardcoded into the UI. Given time I would pull the resources into resource files. 
