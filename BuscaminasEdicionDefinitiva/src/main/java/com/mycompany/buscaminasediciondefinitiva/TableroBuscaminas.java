
package com.mycompany.buscaminasediciondefinitiva;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TableroBuscaminas {
    Casilla[][] casillas;
    
    int numFilas;
    int numColumnas;
    int numMinas;

    int numCasillasAbiertas;
    boolean juegoTerminado;
    
    private Consumer<List<Casilla>> eventoPartidaPerdida;
    private Consumer<List<Casilla>> eventoPartidaGanada;
    private Consumer<Casilla> eventoCasillaAbierta;
    
    public TableroBuscaminas(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.incializarCasillas();
    }
    
    public void incializarCasillas(){
        casillas=new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                casillas[i][j] = new Casilla(i,j);
            }
        }   
        generarMinas();
    }
    
    private void generarMinas(){
        int minasGeneradas=0;
        while (minasGeneradas!=numMinas){
            int posTmpFila=(int)(Math.random()*casillas.length);
            int posTmpColumna=(int)(Math.random()*casillas[0].length);
            if(!casillas[posTmpFila][posTmpColumna].isMina()){
                casillas[posTmpFila][posTmpColumna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumeroMinasAlrededor();
    }
    
    public void imprimirTablero(){
        for (int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                System.out.print(casillas[i][j].isMina()?"*":"0");         
            }
            System.out.println("");
        }
    }
    
    private void imprimirPistas(){
        for (int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                System.out.print(casillas[i][j].getNumMinasAlrededor());         
            }
            System.out.println("");
        }
    }
    
    private void actualizarNumeroMinasAlrededor(){
        for(int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                if (casillas[i][j].isMina()){
                    List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(i,j);
                    casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());
                }
            }
        }
            
    }
    
    private List<Casilla> obtenerCasillasAlrededor(int posFila, int posColumna){
        List<Casilla> listaCasillas=new LinkedList<>();
        for(int i = 0; i < 8; i++){
            int tmpPosFila=posFila;
            int tmpPosColumna=posColumna;
            switch(i){
                case 0: //Arriba
                    tmpPosFila--; 
                    break;
                case 1: //Arriba Derecha
                    tmpPosFila--;tmpPosColumna++;
                    break;
                case 2: //Derecha
                    tmpPosColumna++;
                    break;
                case 3: //Derecha Abajo
                    tmpPosColumna++;tmpPosFila++;
                    break;
                case 4: //Abajo
                    tmpPosFila++;
                    break;
                case 5: //Abajo Izquierda
                    tmpPosFila++;tmpPosColumna--;
                    break;
                case 6: //Izquierda
                    tmpPosColumna--;
                    break;
                case 7: //Izquierda Arriba
                    tmpPosFila--;tmpPosColumna--;
                    break;
            }
            if(tmpPosFila >= 0 && tmpPosFila < this.casillas.length && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length){
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
        }
        return listaCasillas;
    }
    
    List<Casilla> obtenerCasillasConMinas(){
        List<Casilla> casillasConMinas = new LinkedList<>();
        for(int i=0; i<casillas.length; i++){
            for(int j=0; j<casillas[i].length; j++){
                if(casillas[i][j].isMina()){
                    casillasConMinas.add(casillas[i][j]);
                }
            }               
        }
        return casillasConMinas;
    }
    
    public void seleccionarCasilla(int posFila, int posColumna) {
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        }else if(this.casillas[posFila][posColumna].getNumMinasAlrededor()==0){
            marcarCasillaAbierta(posFila,posColumna);
            List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(posFila,posColumna);
            for(Casilla casilla: casillasAlrededor){
                if(!casilla.isAbierta()){
                    seleccionarCasilla(casilla.getPosFila(),casilla.getPosColumna());                    
                }
            }
        }else{
            marcarCasillaAbierta(posFila,posColumna);
        }
        if(partidaGanada()){
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }
    
    void marcarCasillaAbierta(int posFila, int posColumna){
        if(!this.casillas[posFila][posColumna].isAbierta()){
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }
    
    boolean partidaGanada(){
        return numCasillasAbiertas>=(numFilas*numColumnas)-numMinas;
    }
    
    public static void main(String[] args) {
        TableroBuscaminas tablero=new TableroBuscaminas(5,5,5);
        tablero.imprimirTablero();
        System.out.println("=====");
        tablero.imprimirPistas();
    }

    public void setEventoPatidaPerdida(Consumer<List<Casilla>> eventoPatidaPerdida) {
        this.eventoPartidaPerdida = eventoPatidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPatidaGanada(Consumer<List<Casilla>> eventoPatidaGanada) {
        this.eventoPartidaGanada = eventoPatidaGanada;
    }
    
}

