package activeRecord;

public class RealisateurAbsentException extends Exception {

        public RealisateurAbsentException() {
            super("Le realisateur n'existe pas");
        }
}
