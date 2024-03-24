package org.snakeLadder.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Cell {
    private int position ;
    private int nextPosition ;
    CellState state ;
}
