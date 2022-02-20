# Air_Index
Sample project demonstrating socket connection.

<img align="center" width="20%" height="20%"
src="https://github.com/yadavmangesh/Air_Index/blob/master/apk/ezgif.com-gif-maker.gif">
<img align="center" width="20%" height="20%"
src="https://github.com/yadavmangesh/Air_Index/blob/master/apk/ezgif.com-gif-maker%20(1).gif">

# App Architeture 
 The app is based on mvvm koltin. The socket connection methods like connect, dissconnect etc are made lifecycle aware by using `ActivityLifecycleCallbacks`. The App store the aqi   data locally using room db . A Test case is added to validate the categorization of AQI quality i.e good , poor etc. 
  time taken was 1 working day.
 
*Todo: use repository to handle local db and network calls.*
 
