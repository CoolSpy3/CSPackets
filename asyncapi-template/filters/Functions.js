const fs = require('fs')

function getTypes() {
    return JSON.parse(fs.readFileSync('../generator_types.json', 'utf-8'))
}

function getImports(props) {
    var imports = [];
    var types = getTypes()
    for (const [name, prop] of Object.entries(props)) {
        switch (name) {
            case "id":
            case "direction":
                continue;
            default:
                break;
        }
        var imprt = types[prop.const()]['import']
        if (imprt !== "none")
            imports.push(imprt)
    }
    return imports
}

function classTypes(props) {
    var classes = '';
    var types = getTypes()
    for (const [name, prop] of Object.entries(props)) {
        switch (name) {
            case "id":
            case "direction":
                continue;
            default:
                break;
        }
        var cls = types[prop.const()]['class']
        classes += cls
        classes += '.class, '
    }
    return classes.substring(0, classes.length - 2)
}

function getProps(props) {
    var out = [];
    var types = getTypes()
    for (const [name, prop] of Object.entries(props)) {
        switch (name) {
            case "id":
            case "direction":
                continue;
            default:
                break;
        }
        var type = types[prop.const()]['type']
        out.push({ name: name, type: type, comma: ', ' })
    }

    out[out.length - 1]['comma'] = ''
    return out
}

module.exports = { getImports, classTypes, getProps }
