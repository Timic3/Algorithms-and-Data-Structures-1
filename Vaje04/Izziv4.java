package Vaje04;

public class Izziv4 {
    public static void main(String[] args) throws CollectionException {
        // Neurejeno polje
        ArrayPQ<Integer> arrayPQ = new ArrayPQ<>();
        long arrayPQTime = System.nanoTime();
        for (int i = 0; i < 1000; ++i) {
            arrayPQ.enqueue((int) (Math.random() * 2000));
        }
        for (int i = 0; i < 500; ++i) {
            arrayPQ.dequeue();
            arrayPQ.enqueue((int) (Math.random() * 2000));
            arrayPQ.front(); // Ne izpisujem, ker je počasneje.
        }
        arrayPQTime = (System.nanoTime() - arrayPQTime) / 1_000_000;

        // Urejeno polje (sortirano ob vnosu)
        SortedArrayPQ<Integer> sortedArrayPQ = new SortedArrayPQ<>();
        long sortedArrayPQTime = System.nanoTime();
        for (int i = 0; i < 1000; ++i) {
            sortedArrayPQ.enqueue((int) (Math.random() * 2000));
        }
        for (int i = 0; i < 500; ++i) {
            sortedArrayPQ.dequeue();
            sortedArrayPQ.enqueue((int) (Math.random() * 2000));
            sortedArrayPQ.front();
        }
        sortedArrayPQTime = (System.nanoTime() - sortedArrayPQTime) / 1_000_000;

        // Implicitna kopica
        ArrayHeapPQ<Integer> arrayHeapPQ = new ArrayHeapPQ<>();
        long arrayHeapPQTime = System.nanoTime();
        for (int i = 0; i < 1000; ++i) {
            arrayHeapPQ.enqueue((int) (Math.random() * 2000));
        }
        for (int i = 0; i < 500; ++i) {
            arrayHeapPQ.dequeue();
            arrayHeapPQ.enqueue((int) (Math.random() * 2000));
            arrayHeapPQ.front();
        }
        arrayHeapPQTime = (System.nanoTime() - arrayHeapPQTime) / 1_000_000;

        // Eksplicitna kopica
        LinkedHeapPQ<Integer> linkedHeapPQ = new LinkedHeapPQ<>();
        long linkedHeapPQTime = System.nanoTime();
        for (int i = 0; i < 1000; ++i) {
            linkedHeapPQ.enqueue((int) (Math.random() * 2000));
        }
        for (int i = 0; i < 500; ++i) {
            linkedHeapPQ.dequeue();
            linkedHeapPQ.enqueue((int) (Math.random() * 2000));
            linkedHeapPQ.front();
        }
        linkedHeapPQTime = (System.nanoTime() - linkedHeapPQTime) / 1_000_000;

        System.out.println("Objekti: Integer");
        System.out.println("Operacije: 1000 enqueue + 500 (dequeue+enqueue+front)");
        System.out.println("Implementacija                     Čas [ms]           Premikov             Primerjav");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-34s %-18s %-20s %s\n", "Neurejeno polje (64,2x)", arrayPQTime, arrayPQ.getShifts(), arrayPQ.getComparisons());
        System.out.printf("%-34s %-18s %-20s %s\n", "Urejeno polje (64,2x)", sortedArrayPQTime, sortedArrayPQ.getShifts(), sortedArrayPQ.getComparisons());
        System.out.printf("%-34s %-18s %-20s %s\n", "Implicitna kopica (64,2x)", arrayHeapPQTime, arrayHeapPQ.getShifts(), arrayHeapPQ.getComparisons());
        System.out.printf("%-34s %-18s %-20s %s\n", "Eksplicitna kopica", linkedHeapPQTime, linkedHeapPQ.getShifts(), linkedHeapPQ.getComparisons());
    }
}
