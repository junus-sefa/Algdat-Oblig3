package no.oslomet.cs.algdat.Oblig3;

import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>
    {
        private T verdi;
        private Node<T> venstre, høyre;
        private Node<T> forelder;

        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder){
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SBinTre(Comparator<? super T> c){
        rot = null;
        antall = 0;
        comp = c;
    }

    private static <T> boolean inneholder(Node<T> p, T verdi){
        if (p == null){
            return false;
        }
        return verdi.equals(p.verdi) || inneholder(p.venstre,verdi)
                || inneholder(p.høyre,verdi);
    }

    public boolean inneholder(T verdi) {
        if (verdi == null){
            return false;
        }

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0)
                p = p.venstre;
            else if (cmp > 0)
                p = p.høyre;
            else
                return true;
        }
        return false;
    }


    public int antall() {
        return antall;
    }



    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");
        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }
        return s.toString();
    }


    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;
        while (p != null){
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte
        p = new Node<>(verdi, null);
        if (q == null) {
            rot = p;
        } else if (cmp < 0){
            q.venstre = p;
        } else {
            q.høyre = p;
        }

        if(q!= null){
            p.forelder = q;
        } else {
            p.forelder = null;
        }

        antall++;
        endringer++;
        return true;
    }


    public boolean fjern(T verdi) {
        if(verdi == null) return false;

        Node<T> p = rot;
        Node<T> q = null;

        while(p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if(cmp < 0) {
                q = p;p = p.venstre;
            } else if(cmp > 0) {
                q = p;p = p.høyre;
            } else break;
        }

        if(p == null) {
            return false;
        }
        if(p.venstre == null || p.høyre == null) {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if(p == rot) {
                rot = b;
                if(b != null){
                    b.forelder = null;
                }
            } else if(q.venstre == p) {
                q.venstre = b;
                if(b != null){
                    b.forelder = q;
                }
            } else {
                q.høyre = b;
                if(b != null) {
                    b.forelder = q;
                }
            }

            p.forelder = p.venstre = p.høyre = null;
            p.verdi = null;
        } else {
            Node<T> r = p.høyre;
            Node<T> s = p;

            while(r.venstre != null) {
                s = r; r = r.venstre;
            }

            p.verdi = r.verdi;
            if(s != p) {
                s.venstre = r.høyre;
            } else{
                s.høyre = r.høyre;
            }
            if(r.høyre != null) {
                r.høyre.forelder = s;
            }
            r.forelder = r.høyre = null;
            r.verdi = null;
        }
        antall--;
        endringer++;
        return true;
    }



    public int fjernAlle(T verdi) {
        int ant = antall(verdi), teller = 0;
        for (int i = 0; i<ant;i++) {
            fjern(verdi);
            teller++;
        }
        antall = antall-teller;
        return teller;
    }

    public int antall(T verdi) {
        int teller = 0;
        if (tom() || verdi == null){
            return teller;
        }

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp==0){
                teller++;
                p = p.høyre;
            } else if (cmp < 0){
                p = p.venstre;
            } else{
                p = p.høyre;
            }
        }
        return teller;
    }


    public void nullstill(){
        if (!tom()) nullstill(rot);{
            rot = null; antall = 0;
        }
    }

    private void nullstill(Node<T> p){
        if (p.venstre != null){
            nullstill(p.venstre);
            p.venstre = null;
        }
        if (p.høyre != null){
            nullstill(p.høyre);
            p.høyre = null;
        }
        p.verdi = null;
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (p!=null){
            if (p.venstre != null){
                p = p.venstre;
            } else if (p.høyre != null){
                p=p.høyre;
            } else{
                return p;
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) {
            return null;
        }
        if (p.forelder.høyre == p){
            return p.forelder;
        } else if (p.forelder.høyre != null){
            return førstePostorden(p.forelder.høyre);
        } else {
            return p.forelder;
        }
    }


    public void postorden(Oppgave<? super T> oppgave) {
        if (tom()) return;

        Node<T> p = rot;
        while (true)
        {
            if (p.venstre != null){
                p = p.venstre;
            } else if (p.høyre != null){
                p = p.høyre;
            } else{
                break;
            }
        }

        oppgave.utførOppgave(p.verdi);
        while (true){
            if (p == rot){
                break;
            }

            Node<T> f = p.forelder;
            if (f.høyre == null || p == f.høyre){
                p = f;
            } else {
                p = f.høyre;
                while (true)
                {
                    if (p.venstre != null){
                        p = p.venstre;
                    } else if (p.høyre != null){
                        p = p.høyre;
                    } else{
                        break;
                    }
                }
            }
            oppgave.utførOppgave(p.verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }



    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p == null) {
            return;
        }
        if(p.venstre != null) {
            postordenRecursive(p.venstre, oppgave);
        }
        if(p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }




    public ArrayList<T> serialize() {
        ArrayList<T> Liste = new ArrayList<>();

        Deque<Node> køListe = new LinkedList<>();
        if (rot != null)køListe.add(rot);

        while(!køListe.isEmpty()) {
            Node<T> mellom = køListe.poll();
            Liste.add(mellom.verdi);
            if (mellom.venstre != null){
                køListe.add(mellom.venstre);
            }
            if(mellom.høyre!=null) {
                køListe.add(mellom.høyre);
            }
        }
        return Liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> trse = new SBinTre<>(c);
        for (K d : data){
            trse.leggInn(d);
        }
        return trse;
    }


} // ObligSBinTre
