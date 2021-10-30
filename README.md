# crosie-compose-ui
#### A soon to be collection of custom composables.

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

        val scope = rememberCoroutineScope()
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

#### Try it out! 

1. In your settings.gradle, add jitpack under repositories:
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
2. In your app level build.gradle, add this dependency:
```gradle
dependencies {
  ...
  implementation 'com.github.niloc78.crosie-compose-ui:flexible_drawer:1.05'
  ...
}
```




