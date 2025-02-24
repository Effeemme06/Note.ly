package RF.Notely.app.model;

public enum REQUESTS {

    AUTHENTICATE_USER {
        @Override
        public String buildQuery(Object... args) {
            if (args.length == 2 && args[0] instanceof String && args[1] instanceof String) {
                return "?auth&username=" + args[0] + "&password=" + args[1];
            }
            throw new IllegalArgumentException("Invalid arguments for AUTHENTICATE_USER");
        }
    },

    INSERT_NEW_NOTE {
        @Override
        public String buildQuery(Object... args) {
            if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof Integer) {
                return "?newNote&title=" + args[0] + "&body=" + args[1] + "&userID=" + args[2];
            }
            throw new IllegalArgumentException("Invalid arguments for INSERT_NEW_NOTE");
        }
    },
    
    DELETE_NOTE {
    	@Override
        public String buildQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof Integer) {
                return "?deleteNote&noteID=" + args[0];
            }
            throw new IllegalArgumentException("Invalid arguments for DELETE_NOTE");
        }
    },
    
	INSERT_NEW_NOTEPAD {
		@Override
		public String buildQuery(Object... args) {
			if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof Integer) {
				return "?newNotepad&title=" + args[0] + "&description=" + args[1] + "&userID=" + args[2];
			}
			throw new IllegalArgumentException("Invalid arguments for INSERT_NEW_NOTEPAD");
		}
	},
	
	DELETE_NOTEPAD {
		@Override
		public String buildQuery(Object... args) {
			if (args.length == 1 && args[0] instanceof Integer) {
				return "?deleteNotepad&noteID=" + args[0];
			}
			throw new IllegalArgumentException("Invalid arguments for DELETE_NOTEPAD");
		}
	},
	
	DELETE_USER {
		@Override
		public String buildQuery(Object... args) {
			if (args.length == 1 && args[0] instanceof Integer) {
				return "?deleteUser&userID=" + args[0];
			}
			throw new IllegalArgumentException("Invalid arguments for DELETE_USER");
		}
	};

    public abstract String buildQuery(Object... args);
}
