json2view  ![travis-ci](https://travis-ci.org/Avocarrot/json2view.svg)
======

**json2view** is a simple library that can convert a compatible JSON file to an Android view so you can load dynamically the view in your Android app without the need to update the APK.

This removes the hassle of updating, re-compiling and uploading the APK to Google Play everytime you want to make small or big changes in the UI.

<table>
<tr style="border: 0px;">
<td style="border: 0px;">
<ul class="task-list">
<li><a href="#common-use-cases">Common use cases</a></li>
<li><a href="#how-it-works">How it works</a></li>
<li><a href="#examples">Examples</a></li>
<li><a href="#installation">Installation</a></li>
<li><a href="#getting-started">Getting started</a></li>
<li><a href="#contributing">Contributing</a></li>
<li><a href="#license">License</a></li>
</ul>
</td>
<td style="width:60%; border: 0px; text-align:right;">
<img alt="json2view logo" src="https://github.com/Avocarrot/json2view/blob/master/example_assets/json2view.png" width="250px"/>
</td>
</tr>
</table>


## Common use cases

1. If you need to A/B test different UI designs without the need to re-upload your APK.
2. If your app's UI changes dynamically based on different users or scenarios.
3. If you need to deploy UI fixes quickly and in real-time.

In the case of [Avocarrot](http://www.avocarrot.com), we are using json2view to run A/B test experiments and quickly deploy UI enhancements that improve revenue performance of native ads integrations in our network.

## How it works

You can parse any xml through the json2view library to create a JSON that will be used at runtime to dynamically generate the Android UI using native code. This JSON can be hosted anywhere on the internet (your own server, Dropbox, Github Pages etc.) and can be fetched in your app at any point you decide.

<img alt="how it works schematic" src="https://github.com/Avocarrot/json2view/blob/master/example_assets/how_it_works.png" width="700px"/>

Note: Runtime creation of a view without the precompiled version of xml in apk (res/layout), especially for highly complex layouts, can be a potential latency issue.

## Examples

### Changing text color

Using json2view to change text color, background color and position of a view. [(more details)](https://github.com/Avocarrot/json2view/wiki/Changing-Properties)

![output](https://github.com/Avocarrot/json2view/blob/master/example_assets/test00.png)

### Reorganizing the layout

Using json2view to reorganize the layout of a screen. [(more details)](https://github.com/Avocarrot/json2view/wiki/Changing-Layouts)

![output](https://github.com/Avocarrot/json2view/blob/master/example_assets/test01.png)

You can find more help and examples in the [wiki](https://github.com/Avocarrot/json2view/wiki).

Also, a sample project for quick use of the lib can be found in the [sample submodule](https://github.com/Avocarrot/json2view/tree/master/sample)

## Installation
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

## Getting started

### Basic JSON format
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


### Loading dynamic layout
create and attach view in the specific Parent (created from xml) <br/>
```java
ViewParent viewParent = (ViewParent) findViewById(R.id.parent_view_id)
JSONObject jsonObject = ... // load from network, sdcard etc
View sampleView = DynamicView.createView(this, jsonObject, viewParent);
```

You can check some example use cases in the wiki [here](https://github.com/Avocarrot/json2view/wiki/Creating-Dynamic-Layouts)


## Contributing

1. Fork the repo
2. Apply your changes
3. Write tests
4. Submit your pull request

For feedback or suggestions you can drop us a line at support@avocarrot.com

## License
[The MIT License (MIT)](https://github.com/Avocarrot/json2view/blob/master/LICENSE)


Also check out the project at [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-json2view-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1453)
