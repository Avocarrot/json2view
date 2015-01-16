
def inputFile = new File(this.args[0])

// Some more validations.
if (!inputFile.exists()) {
    // Nothing to process, return
    println "The input file doesn't exist. Process incomplete | " + this.args[0]
    return
}

def layout = new XmlParser().parse(inputFile)

println new groovy.json.JsonBuilder( createView(layout) ).toString();

View createView (node) {

    def name = "";
    def properties = [];
    def views = [];

    name = node.name().toString();

    node.attributes().each { t ->
        properties.push( createProperty(t) )
    }

    node.value().each { t ->
        views.push( createView(t) );
    }

    return new View( name, properties, views );

}

Property createProperty (attribute) {

    def name = attribute.key.localPart;
    def type = "string";
    def value =  attribute.value;

    switch(name.toUpperCase()) {
        case "ID":
        case "LAYOUT_ABOVE":
        case "LAYOUT_ALIGNBASELINE":
        case "LAYOUT_ALIGNBOTTOM":
        case "LAYOUT_ALIGNEND":
        case "LAYOUT_ALIGNLEFT":
        case "LAYOUT_ALIGNRIGHT":
        case "LAYOUT_ALIGNSTART":
        case "LAYOUT_ALIGNTOP":
        case "LAYOUT_BELOW":
        case "LAYOUT_TOENDOF":
        case "LAYOUT_TOLEFTOF":
        case "LAYOUT_TORIGHTOF":
        case "LAYOUT_TOSTARTOF":
            type = "ref";
            try {
                value = value.split("/")[1];
            } catch (Exception e) {}
            break;
        case "BACKGROUND":
        case "TEXTCOLOR":
            type = "color";
            break;
        case "LAYOUT_WIDTH":
        case "LAYOUT_HEIGHT":
        case "PADDING_LEFT":
        case "PADDING_RIGHT":
        case "PADDING_TOP":
        case "PADDING_BOTTOM":
        case "PADDING":
        case "TEXTSIZE":
            type = "dimen";
            break;
        case "LAYOUT_ALIGNWITHPARENTIFMISSING":
        case "LAYOUT_CENTERHORIZONTAL":
        case "LAYOUT_CENTERINPARENT":
        case "LAYOUT_CENTERVERTICAL":
        case "LAYOUT_ALIGNPARENTBOTTOM":
        case "LAYOUT_ALIGNPARENTEND":
        case "LAYOUT_ALIGNPARENTLEFT":
        case "LAYOUT_ALIGNPARENTRIGHT":
        case "LAYOUT_ALIGNPARENTSTART":
        case "LAYOUT_ALIGNPARENTTOP":
            type = "BOOLEAN";
            break;
    }

    return new Property( name, type, value );

}

@groovy.transform.Canonical
@ groovy.transform.ToString(excludes='view')
class View {
    String widget
    List<Property> properties
    List<View> views
}
@groovy.transform.Canonical
class Property {
    String name
    String type
    String value
}
