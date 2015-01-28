json2view  ![travis-ci](https://magnum.travis-ci.com/Avocarrot/json2view.svg?token=JZNsn6pty78ndT1Z2naj&branch=master)
======

# Updating Native Android UI

**json2view** is a simple library that can convert a compatible JSON file to an Android view so you can load dynamically the view in your Android app without the need to update the APK.

You can parse any xml through the json2view library to create a JSON that will be used at runtime to dynamically generate the Android UI using native code. This JSON can be hosted anywhere on the internet (your own server, Dropbox, Github Pages etc.) and can be fetched in your app at any point you decide.

This removes the hassle everytime you want to make small changes in UI to have to update the APK which includes re-compiling and uploading to Google Play as well as every single user manually deciding whether to update your app or not (if they have opted-out from auto-update).

Hope you find it useful and any feedback or PRs are more than welcome!

## Pros
* Change layout for a view without the need to update the apk

## Cons
* Runtime creation of view without the precompiled version of xml in apk (res/layout) and for highly complex layouts this can be a potential latency issue

You can find more help and examples in the [wiki](https://github.com/Avocarrot/json2view/wiki).

Also, a sample project for quick use of the lib can be found in the [sample submodule](https://github.com/Avocarrot/json2view/tree/master/sample)


# How It Works

![Flow](https://github.com/Avocarrot/json2view/blob/master/example_assets/json2viewFlow.jpg)

Some examples when json2view is useful include having just released a new feature and your mobile analytics show a slow user adoption, so you might want to deploy a quick UI fix to draw better attention on the call-to-actions without having to wait for the next big APK update. In the case of [Avocarrot](http://www.avocarrot.com), we are using json2view to run A/B test experiments and quickly deploy UI enhancements that improve revenue performance of native ads integrations in our network.


# Installation
- Download project
```
git clone https://github.com/Avocarrot/json2view.git
```


- add json2view in your project by adding in your settings.gradle
```
include ':json2view'
project(':json2view'*).projectDir = new File(settingsDir, '$(json2viewPath)/json2view/json2view')
```


- add in build.gradle of application module (not project build.gradle) in `dependencies` section

```
compile project(':json2view')
```


# Basic JSON Format
The input JSON has 3 fields for every view we want to create :

* `widget` : canonicalName of View (for views in package `android.widget` you can ommit `android.widget`)
* `properties` : list of properties for the view. ([Available Properties](https://github.com/Avocarrot/json2view/wiki/Available-Properties)) By default we add `layout_width` & `layout_height` with value `wrap_content'
* `views` : children views for ViewGroup _(optional)_

eg. JSON to create a empty TextView
```javascript
{
    "widget": "android.widgetTextView",
	"properties": []
}
```

eg. JSON to create a TextView with textSize : 12sp and text : "Hello Avocarrot!"
```javascript
{
    "widget": "TextView",
	"properties": [
	    {"name":"textSize", "type": "dimen", "value":"13sp"},
	    {"name":"text", "type": "string", "value":"Hello Avocarrot!"}
	]
}
```

You can find some examples for xml to JSON conversions in the wiki [here](https://github.com/Avocarrot/json2view/wiki/Xml-2-JSON-Examples)

You can use *ConvertXML2JSON.groovy* (from ./utils) to convert any android xml to json2view valid JSON file
_(the script needs further development to create a valid JSON for <b>every</b> case)_ <br/>
try : <br/>

```
./gradlew runScript -Pxml=./pathToInputXmlFile.xml
```
from the root folder of the project


# Loading dynamic Layout
create and attach view in the specific Parent (created from xml) <br/>
```java
ViewParent viewParent = (ViewParent) findViewById(R.id.parent_view_id)
JSONObject jsonObject = ... // load from network, sdcard etc
View sampleView = DynamicView.createView(this, jsonObject, viewParent);
```

You can check some example use cases in the wiki [here](https://github.com/Avocarrot/json2view/wiki/Creating-Dynamic-Layouts)


# Contact

For feedback or suggestions you can drop us a line at support@avocarrot.com

# License
[The MIT License (MIT)](https://github.com/Avocarrot/json2view/blob/master/LICENSE)
