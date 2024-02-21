public class CustomException {

    static class MapLoadingException extends Exception{
        public MapLoadingException(String message){
            super(message);
        }
    }

    static class MissingStartPointException extends Exception{
        public MissingStartPointException(String message){
            super(message);
        }
    }

    static class MissingFinishPointException extends Exception{
        public MissingFinishPointException(String message){
            super(message);
        }
    }


}
