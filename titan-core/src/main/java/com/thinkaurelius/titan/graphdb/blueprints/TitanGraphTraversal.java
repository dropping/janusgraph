package com.thinkaurelius.titan.graphdb.blueprints;

import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.tinkerpop.gremlin.process.Step;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.graph.GraphTraversal;
import com.tinkerpop.gremlin.process.graph.step.map.VertexStep;
import com.tinkerpop.gremlin.process.graph.util.DefaultGraphTraversal;
import com.tinkerpop.gremlin.process.util.TraversalHelper;
import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.Vertex;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public class TitanGraphTraversal<S, E> extends DefaultGraphTraversal<S, E> {

    public TitanGraphTraversal(final TitanTransaction graph, final Class<? extends Element> elementClass) {
        super(graph);
        getStrategies().register(TitanGraphStepStrategy.instance());
        getStrategies().register(TitanVertexStepStrategy.instance());
        addStep(new TitanGraphStep<>(this, elementClass));
    }

    @Override
    public <E2> GraphTraversal<S, E2> addStep(final Step<?, E2> step) {
        if (this.getStrategies().complete()) throw Exceptions.traversalIsLocked();
        return super.addStep(TitanTraversal.replaceStep(this,step));
    }





}