package RF.Notely.app.model;
import RF.Notely.app.model.Query;

public enum QueryHandler {

    AUTHENTICATE_USER {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof String ) {
            	Query query = new Query(RequestMethod.GET).setRoute("auth");
                query.addParameter("token", args[0]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for AUTHENTICATE_USER");
        }
    },

    INSERT_NEW_NOTE {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof Integer) {
//                return "?newNote&title=" + args[0] + "&body=" + args[1] + "&userID=" + args[2];
                Query query = new Query(RequestMethod.POST).setRoute("newNote");
                query.addParameter("title", args[0]);
                query.addParameter("body", args[1]);
                query.addParameter("notepad_id", args[2]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for INSERT_NEW_NOTE");
        }
    },

    DELETE_NOTE {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof Integer) {
                Query query = new Query(RequestMethod.POST).setRoute("deleteNote");
                query.addParameter("noteID", args[0]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for DELETE_NOTE");
        }
    },

    INSERT_NEW_NOTEPAD {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof Integer) {
                Query query = new Query(RequestMethod.POST).setRoute("newNotepad");
                query.addParameter("title", args[0]);
                query.addParameter("description", args[1]);
                query.addParameter("userID", args[2]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for INSERT_NEW_NOTEPAD");
        }
    },

    DELETE_NOTEPAD {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof Integer) {
                Query query = new Query(RequestMethod.POST).setRoute("deleteNotepad");
                query.addParameter("notepadID", args[0]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for DELETE_NOTEPAD");
        }
    },

    ADD_USER {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof String) {
                Query query = new Query(RequestMethod.POST).setRoute("addUser");
                query.addParameter("username", args[0]);
                query.addParameter("name", args[1]);
                query.addParameter("surname", args[2]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for ADD_USER");
        }
    },

    CHECK_USERNAME {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof String) {
                Query query = new Query(RequestMethod.GET).setRoute("checkUsername");
                query.addParameter("username", args[0]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for CHECK_USERNAME");
        }
    },

    DELETE_USER {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof Integer) {
                Query query = new Query(RequestMethod.POST).setRoute("deleteUser");
                query.addParameter("userID", args[0]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for DELETE_USER");
        }
    },

    GET_NOTES {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof String) {
                Query query = new Query(RequestMethod.GET).setRoute("fetchNotes");
                query.addParameter("token", args[0]);
                return query;
            }else {
            	return new Query(RequestMethod.GET).setRoute("fetchNotes");            	
            }
        }
    },

    GET_NOTEPADS {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 1 && args[0] instanceof String) {
                Query query = new Query(RequestMethod.GET).setRoute("fetchNotepads");
                query.addParameter("token", args[0]);
                return query;
            }else {
            	return new Query(RequestMethod.GET).setRoute("fetchNotepads");            	
            }
        }
    },

    CREATE_NOTEPAD {
        @Override
        public Query newQuery(Object... args) {
            if (args.length == 2 && args[0] instanceof String && args[1] instanceof String) {
                Query query = new Query(RequestMethod.POST).setRoute("createNotepad");
                query.addParameter("title", args[0]);
                query.addParameter("description", args[1]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for CREATE_NOTEPAD");
        }
    },
	SHARE_NOTE {
    	@Override
        public Query newQuery(Object... args) {
            if (args.length == 3 && args[0] instanceof Integer && args[1] instanceof String && args[2] instanceof Integer) {
                Query query = new Query(RequestMethod.PUT).setRoute("shareNote");
                query.addParameter("noteID", args[0]);
                query.addParameter("usernameTarget", args[1]);
                query.addParameter("perx", args[2]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for SHARE_NOTE");
        }
	}, 
	EDIT_NOTE_TITLE {
		@Override
        public Query newQuery(Object... args) {
            if (args.length == 2 && args[0] instanceof String && args[1] instanceof Integer) {
                Query query = new Query(RequestMethod.PUT).setRoute("editNoteTitle");
                query.addParameter("newTitle", args[0]);
                query.addParameter("noteID", args[1]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for EDIT_NOTE_TITLE");
        }
		
	}, 
	EDIT_NOTEPAD_TITLE {
		@Override
		public Query newQuery(Object... args) {
			if (args.length == 2 && args[0] instanceof String && args[1] instanceof Integer) {
				Query query = new Query(RequestMethod.PUT).setRoute("editNotepadTitle");
				query.addParameter("newTitle", args[0]);
				query.addParameter("notepadID", args[1]);
				return query;
			}
			throw new IllegalArgumentException("Invalid arguments for EDIT_NOTE_TITLE");
		}
		
	}, 
	EDIT_NOTE_BODY {
		@Override
        public Query newQuery(Object... args) {
            if (args.length == 2 && args[0] instanceof String && args[1] instanceof Integer) {
                Query query = new Query(RequestMethod.PUT).setRoute("editNoteBody");
                query.addParameter("newBody", args[0]);
                query.addParameter("noteID", args[1]);
                return query;
            }
            throw new IllegalArgumentException("Invalid arguments for EDIT_NOTE_BODY");
        }
	};
	
	
	

    public abstract Query newQuery(Object... args);
}
