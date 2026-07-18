package org.ignaciorodriguez.utils;

import java.util.List;
import org.ignaciorodriguez.modelo.Determinacion;

public class ListUtils {
    public static void addIfcondition(Determinacion element, List list, boolean condition){
        if(condition){
            list.add(element);
        }
    }
}
