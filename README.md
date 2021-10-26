# Last Twenty - See your last 20 notifications

## Tech stack & Open source libraries
- Minimum SDK level 21.
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- View binding - View binding is a feature that allows you to more easily write code that interacts with views.
- JetPack
  - LiveData - For observer pattern on UI layer
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.

- Architecture
  - MVVM Architecture (View - ViewModel - Model)
  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern

- Testing
  - [MockK](https://mockk.io/)
  - [Robolectric](http://robolectric.org/)
  - JUnit


Notifications Demo

https://user-images.githubusercontent.com/5092994/138938388-4c2186bb-7365-4996-9f0a-8e9733349129.mp4

New Notification Received

https://user-images.githubusercontent.com/5092994/138938468-ec0a7d2b-3f35-4981-a4fe-6488bd34f951.mp4

Notification removed

https://user-images.githubusercontent.com/5092994/138938575-c94a3537-bd7c-440d-8a66-d866bb0e56d5.mp4

Orientation config changes

https://user-images.githubusercontent.com/5092994/138938615-07f3dad8-bd57-49e8-9ef8-5ac92752df28.mp4

Change theme config changes

https://user-images.githubusercontent.com/5092994/138938676-d528e144-341f-46e1-b03e-5ee1ea77af69.mp4












