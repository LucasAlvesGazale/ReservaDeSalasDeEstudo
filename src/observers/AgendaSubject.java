package src.observers;

public interface AgendaSubject {
    public void addObserver();
    public void removeObserver();
    public void notifyObservers();
}
