# crosie-compose-ui
#### A soon to be collection of custom composables.

1. Before you add any dependencies, make sure that in your settings.gradle, you add jitpack under repositories:
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // add jitpack here 👇🏽
        maven { url 'https://jitpack.io' }
       ...
    }
} 
...
```

## Current Features

### FlexibleDrawer
#### A drawer with unique features while keeping the default features of ModalDrawer(including swipe, drag, scrim, etc)

| Bonus Features (excluding ModalDrawer default features) | Param |
| ---------------- | -------------|
| Ability to push main content to the side | `pushAside : Boolean, default = false` |
| Specify the portion of the screen the opened drawer should take up | `drawerPortion : Float (Range 0.0 - 1.0), default = 0.75` |
| Specify the padding at the end of the opened drawer | `endDrawerPadding : Dp, default = 0.dp` |

#### Example Implementation
![Alt Text](https://media.giphy.com/media/OnBn6L2Crnwl5XMM13/giphy.gif)
```kotlin
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun navDrawer() {
    ComposeTestingTheme {
        val drawerState = rememberDrawerState2(initialValue = DrawerValue.Closed)
        
        FlexibleDrawer(drawerState = drawerState,
            drawerContent = {
                //whatever is inside the drawer
            },
            pushAside = true,
            drawerPortion = 0.75f
        ) {
            //stuff outside drawer
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                Text(text = "Helllo!!", color = Color.Black)
            }
        }
    }
}
```

#### Try FlexibleDrawer out! 

In your app level build.gradle, add this dependency(latest version = 1.1.1):
```gradle
dependencies {
  ...
  implementation 'com.github.niloc78.crosie-compose-ui:flexible_drawer:$latestVersion'
  ...
}
```

### CircularProgressButton
#### A customizable, timed circular progres button with unique features.

| Features | Params |
| ---------------- | -------------|
| Do something after the specified duration pressed | `onTimedLongPress : () -> Unit, default = {}` |
| Specify the duration to invoke the timed long press | `timer : Long, default = 2000L` |
| Initial state of the button) | `isTimedLongPressed : MutableState<Boolean>, default = remember { mutableStateOf(false) }` |
| Specify the progress stroke width, stroke start/end color (gradients), circle radius, pressed/unpressed circle color,  | `strokeWidth : Float , default = 35f` <br/> `strokeStartColor : Color, default = Color.Blue` <br/> `strokeEndColor : Color, default = Color.Cyan` <br/> `radius : Float, default = 60f` <br/> `circleColor : Color, default = Color.Cyan` <br/> `pressedColor : Color, default = Color.Green` |
| Specify padding between the progress stroke and the inner circle | `innerPadding : Float, default = 30f` |
| Specify the end of the progress stroke should be rounded, or edged | `roundedStrokeCap : Boolean, default = true` |
| Specify whether or not the progress stroke should still show after onTimedLongPressed is invoked | `showPressedStroke : Boolean, default = true` |
| Specify the progress stroke decrease speed when press is cancelled | `strokeDecreaseTimer : Int, default = 300` |
| Specify if the press should be cancelled after tapping when its pressed | `unPressOnTap : Boolean, default = false` |
| Specify if the inner circle should overlap the progress stroke (with negative padding) | `overlapCircleOverStroke : Boolean, default = false` |

#### Example Implementation
![Alt Text](https://media.giphy.com/media/KxYYIo3Ukt3xXMbNUK/giphy.gif)
```kotlin
@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CircularProgressButtonExample() {
    ComposeTestingTheme() {
        val isPressed = remember { mutableStateOf(true) }
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            CircularProgressButton(modifier = Modifier,
                isTimedLongPressed = isPressed,
                strokeStartColor = Color.Blue,
                strokeEndColor = Color.Cyan,
                pressedColor = Color.Green,
                circleColor = Color.Cyan,
                strokeWidth = 35f,
                radius = 60f,
                innerPadding = 30f,
                unPressOnTap = true,
                timer = 2000L) {
                //onTimedLongClicked invoke here
            }

            Text(text = if(isPressed.value)"I was pressed!" else "I am not pressed")
        }
    }
}
```

#### Try CircularProgressButton out! 

In your app level build.gradle, add this dependency(latest version = 1.1.1):
```gradle
dependencies {
  ...
  implementation 'com.github.niloc78.crosie-compose-ui:circular_progress_button:$latestVersion'
  ...
}
```




