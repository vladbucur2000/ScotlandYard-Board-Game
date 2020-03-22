import com.google.common.collect.ImmutableSet;
import com.google.common.graph.*;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.OurDijkstra;

/** RUN => EDIT CONFIGURATIONS => VM OPTIONS insert " -ea  " */
/** NOW ASSERTS WILL WORK */

class TestDijkstra
{
    private static void testing (int[] x, int[] y, int size) {

        for (int i = 0; i <= size; ++i)
            assert x[i] == y[i] : "AI GRESIT PATROANE";
    }

    public static void main( String args[] )
    {
        OurDijkstra dijkstra = new OurDijkstra();

        /** TEST 1 **/
        ImmutableSet<ScotlandYard.Transport> standard = ImmutableSet.of(ScotlandYard.Transport.TAXI); //any transport type
        MutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = ValueGraphBuilder.undirected().allowsSelfLoops(true).build();
        graph.putEdgeValue(1, 2, standard);
        graph.putEdgeValue(1, 3, standard);
        graph.putEdgeValue(3, 2, standard);
        graph.putEdgeValue(5, 6, standard);
        graph.putEdgeValue(4, 5, standard);

        int[] distances = dijkstra.compute(ImmutableValueGraph.copyOf(graph), 1);
        int[] answer = new int[]{Integer.MAX_VALUE, 0, 1, 1, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

        testing(distances, answer, graph.nodes().size());

        /** TEST 2 **/

        graph = ValueGraphBuilder.undirected().allowsSelfLoops(true).build();
        graph.putEdgeValue(3, 2, standard);
        graph.putEdgeValue(3, 4, standard);
        graph.putEdgeValue(3, 1, standard);
        graph.putEdgeValue(3, 5, standard);
        graph.putEdgeValue(4, 6, standard);
        graph.putEdgeValue(1, 7, standard);
        graph.putEdgeValue(6, 8, standard);

        distances = dijkstra.compute(ImmutableValueGraph.copyOf(graph), 3);
        answer = new int[]{Integer.MAX_VALUE, 1, 1, 0, 1, 1, 2, 2, 3};

        testing(distances, answer, graph.nodes().size());

    }
} 