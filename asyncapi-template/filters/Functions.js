const fs = require('fs')

function getTypes() {
    return JSON.parse(fs.readFileSync('../generator_types.json', 'utf-8'))
}

function getImports(props) {
    try {

        var imports = [];
        var types = getTypes()

        for (const [name, prop] of Object.entries(props)) {

            switch (name) {
                case 'id':
                case 'direction':
                    continue;
                default:
                    break;
            }

            var imprt = types[prop.const()]['import']

            if (imprt !== 'none')
                imports.push(imprt)
        }

        return imports

    } catch (err) {

        console.log("Error occured! Property dump:")
        console.log(props)

        throw err
    }
}

function classTypes(props) {
    try {

        var classes = '';
        var types = getTypes()

        for (const [name, prop] of Object.entries(props)) {

            switch (name) {
                case 'id':
                case 'direction':
                    continue;
                default:
                    break;
            }

            var cls = types[prop.const()]['class']
            classes += cls
            classes += '.class, '

        }

        return classes.substring(0, classes.length - 2)

    } catch (err) {

        console.log("Error occured! Property dump:")
        console.log(props)

        throw err
    }
}

function getProps(props) {
    try {

        var out = [];
        var types = getTypes()

        for (const [name, prop] of Object.entries(props)) {
            switch (name) {
                case 'id':
                case 'direction':
                    continue;
                default:
                    break;
            }
            var type = types[prop.const()]['type']
            out.push({ name: name, type: type, comma: ', ' })
        }

        out[out.length - 1]['comma'] = ''

        return out
    } catch (err) {

        console.log("Error occured! Property dump:")
        console.log(props)

        throw err

    }
}

function sidedPackets(asyncapi, direction) {
    try {

        var out = []

        for (const [name, schema] of asyncapi.allSchemas()) {
            if ('direction' in schema.properties() && schema.property('direction').const() === direction)
                out.push({ name: name, id: schema.property('id').const() })
        }

        return out

    } catch (err) {

        console.log("Error occured! Property dump:")
        console.log(props)

        throw err

    }
}

function clientboundPackets(asyncapi) {
    return sidedPackets(asyncapi, 'clientbound')
}

function serverboundPackets(asyncapi) {
    return sidedPackets(asyncapi, 'serverbound')
}

module.exports = { getImports, classTypes, getProps, clientboundPackets, serverboundPackets }
