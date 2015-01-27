koukouroukou  ![travis-ci](https://magnum.travis-ci.com/Avocarrot/koukouroukou.svg?token=JZNsn6pty78ndT1Z2naj&branch=master)
============

# Dynamic layouts for Android

Thanks for taking a look on **Json2View**.
Json2View can convert a compatible json file to an android view so you can load dynamic view in your android application.

# Installation
- Download project
```git clone https://github.com/Avocarrot/koukouroukou.git```

- add json2view in your project by adding in your settings.gradle
```include ':json2view'
project(':json2view').projectDir = new File(settingsDir, '$(koukouroukouPath)/koukouroukou/json2view')```

- add in build.gradle of application module (not project build.gradle) in `dependencies` section
```compile project(':json2view')```

# Usage
create and attach view in the specific Parent (created from xml) <br/>
```java
ViewParent viewParent = (ViewParent) findViewById(R.id.parent_view_id)
JSONObject jsonObject = ... // load from network, sdcard etc
View sampleView = DynamicView.createView(this, jsonObject, viewParent);
```

you can check more example in [Usage](https://github.com/Avocarrot/koukouroukou/wiki/Usage)

# Basic Json Format
The input json must have 3 fields for every View you need to add. To add a TextView
```javascript
{
    "widget": "android.widget.TextView",
	"properties": [],
	"views":[] /*optional*/
}
```

# Supported Properties
Most of the properties for Android View are supported. <br/>
You can find the full list [Available Properties](https://github.com/Avocarrot/koukouroukou/wiki/Available-Properties)


## pros
* change layout for a view without update apk

## cons
* runtime creation of view while android xml are precompiled in apk (res/layout)

Help is available on the [wiki](https://github.com/Avocarrot/koukouroukou/wiki).

A quick use of the lib is available in [Sample](https://github.com/Avocarrot/koukouroukou/tree/master/sample) submodule

# License
[The MIT License (MIT)](https://github.com/Avocarrot/koukouroukou/blob/master/LICENSE)