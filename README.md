koukouroukou  ![travis-ci](https://magnum.travis-ci.com/Avocarrot/koukouroukou.svg?token=JZNsn6pty78ndT1Z2naj&branch=master)
============

# Dynamic layouts for Android

Thanks for taking a look on **Json2View**.
Json2View can convert a compatible json file to an android view so you can load dynamic view in your android application.

# Installation
- Download project
```
git clone https://github.com/Avocarrot/koukouroukou.git
```


- add json2view in your project by adding in your settings.gradle
```
include ':json2view'
project(':json2view'*).projectDir = new File(settingsDir, '$(koukouroukouPath)/koukouroukou/json2view')
```


- add in build.gradle of application module (not project build.gradle) in `dependencies` section

```
compile project(':json2view')
```


# Usage
create and attach view in the specific Parent (created from xml) <br/>
```java
ViewParent viewParent = (ViewParent) findViewById(R.id.parent_view_id)
JSONObject jsonObject = ... // load from network, sdcard etc
View sampleView = DynamicView.createView(this, jsonObject, viewParent);
```

you can check more example in [Usage](https://github.com/Avocarrot/koukouroukou/wiki/Usage)

# Basic Json Format
The input json has 3 fields for every view we want to create :

* `widget` : canonicalName of View (for views in package `android.widget` you can ommit `android.widget`)
* `properties` : list of properties for the view. ([Available Properties](https://github.com/Avocarrot/koukouroukou/wiki/Available-Properties)) By default we add `layout_width` & `layout_height` with value `wrap_content'
* `views` : children views for ViewGroup _(optional)_

eg. json to create a empty TextView
```javascript
{
    "widget": "android.widgetTextView",
	"properties": []
}
```

eg. json to create a TextView with textSize : 12sp and text : "Hello Avocarrot!"
```javascript
{
    "widget": "TextView",
	"properties": [
	    {"name":"textSize", "type": "dimen", "value":"13sp"},
	    {"name":"text", "type": "string", "value":"Hello Avocarrot!"}
	]
}
```

You can use *ConvertXML2JSON.groovy* script in utils to convert any xml to json2view valid json input file <br/>
_(the script needs further development to create a valid json for *every* case)_ <br/>
try : <br/>

```
./gradlew runScript -Pxml=./pathToInputXmlFile.xml
```
from the root folder of the project



## pros
* change layout for a view without update apk

## cons
* runtime creation of view while android xml are precompiled in apk (res/layout)

Help is available on the [wiki](https://github.com/Avocarrot/koukouroukou/wiki).

A quick use of the lib is available in [Sample](https://github.com/Avocarrot/koukouroukou/tree/master/sample) submodule

# License
[The MIT License (MIT)](https://github.com/Avocarrot/koukouroukou/blob/master/LICENSE)