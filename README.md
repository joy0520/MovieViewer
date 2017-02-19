# Project 1 - Flicks

Flicks shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: 16.5 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current movies** from the Movie Database API
* [X] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [X] For each movie displayed, user can see the following details:
  * [X] Title, Poster Image, Overview (Portrait mode)
  * [X] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [X] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [X] Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* [X] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [X] When viewing a popular movie (i.e. a movie voted for more than '7' stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [X] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [X] Overlay a play icon for videos that can be played.
    * [X] More popular movies should start a separate activity that plays the video immediately.
    * [X] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [X] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code. As a current Android app developer, this is not that helpful as expected.
* [X] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [ ] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.

The following **additional** features are implemented:

* [ ] 1 hour try to make the list remain the same position after roattion or any other configuration changed, but failed.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/9imLA4o.gif' title='Project 1- Flicks' />

GIF created with [Gif Maker - Gif Editor](https://play.google.com/store/apps/details?id=com.kayak.studio.gifmaker&hl=zh_HK).

## Notes

Describe any challenges encountered while building the app.

* I adjust the criteria to 7 since almost all the now-playing movies has average vote higher than 5.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Android Support 7- RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html) - Better ListView
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) - Transform an image's border integrated with Picasso usage
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library that can convert Java Objects into JSON and back.
- [Android Asynchronous Http Client](http://loopj.com/android-async-http/) - A Callback-Based Http Client Library for Android
- [ButterKnife](http://jakewharton.github.io/butterknife/) - Field and method binding for Android views
- [YouTubeAndroidPlayerApi](https://developers.google.com/youtube/android/player/) - Play Youtube vedio in app

## License

    Copyright [2017] [Flicks]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.