{%- set props = schema.properties() | getProps -%}
// PLEASE DO NOT EDIT THIS FILE!!!
// IT IS AUTOMATICALLY GENERATED BY THE 'generatePacketFiles' GRADLE TASK
// FROM THE TEMPLATE FILE 'asyncapi-template/template/$$schema$$.java'.
// EDIT THAT FILE INSTEAD.
package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

{% for fqn in schema.properties() | getImports -%}
import {{ fqn }};
{%- endfor %}

@PacketSpec(types = { {{ schema.properties() | classTypes }} }, direction = PacketDirection.{{ schema.property('direction').const() | upper }})
public class {{ schemaName }} extends Packet
{

    {% for prop in props -%}
    public final {{prop['type']}} {{prop['name']}};
    {%- endfor %}

    public {{ schemaName }}({% for prop in props -%}{{prop['type']}} {{prop['name']}}{{prop['comma']}}{% endfor -%})
    {
        {% for prop in props -%}
        this.{{prop['name']}} = {{prop['name']}};
        {%- endfor %}
    }

    public {{schemaName}}(Object[] args)
    {
        this({% for i in range(0, props.length) -%}({{props[i]['type']}}) args[{{i}}]{{props[i]['comma']}}{% endfor -%});
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] { {%- for prop in props -%}{{prop['name']}}{{prop['comma']}}{%- endfor -%} };
    }

}
