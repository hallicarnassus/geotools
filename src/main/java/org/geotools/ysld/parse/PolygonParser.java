package org.geotools.ysld.parse;

import org.geotools.styling.*;
import org.geotools.ysld.YamlMap;
import org.geotools.ysld.YamlObject;

public class PolygonParser extends SymbolizerParser<PolygonSymbolizer> {

    public PolygonParser(Rule rule, Factory factory) {
        super(rule, factory.style.createPolygonSymbolizer(), factory);
    }

    @Override
    public void handle(YamlObject<?> obj, YamlParseContext context) {
        super.handle(obj, context);

        YamlMap map = obj.map();
        context.push(new StrokeParser(factory) {
            @Override
            protected void stroke(Stroke stroke) {
                sym.setStroke(stroke);
            }
        });
        context.push(new FillParser(factory) {
            @Override
            protected void fill(Fill fill) {
                sym.setFill(fill);
            }
        });

        if (map.has("offset")) {
            sym.setPerpendicularOffset(Util.expression(map.str("offset"), factory));
        }
        if (map.has("displacement")) {
            sym.setDisplacement(Util.displacement(map.str("displacement"), factory));
        }
    }
}
