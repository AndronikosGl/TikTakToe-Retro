/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package TikTakToeRetro;

/**
 *
 * @author andygl
 */
public interface MoveStrategy {
    int[] move(Board board, char pcsymb, char usrsymb);
}
