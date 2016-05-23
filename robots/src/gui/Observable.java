package gui;


public interface Observable {

    void addObserver(Observer o);
    void notifyObservers();
}
