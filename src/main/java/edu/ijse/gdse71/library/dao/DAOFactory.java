package edu.ijse.gdse71.library.dao;

import edu.ijse.gdse71.library.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory(){
    }

    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        AUTHOR,
        AUTHOR_DETAILS,
        BOOK,
        BOOKSHELF,
        CATEGORY,
        CATEGORY_DETAILS,
        FINE,
        LOAN,
        MEMBER,
        PAYMENT,
        PUBLISHER,
        RESERVATION,
        RETURN,
        USER

    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){

            case AUTHOR:
                return new AuthorDAOImpl();
            case AUTHOR_DETAILS:
                return new AuthorDetailsDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case BOOKSHELF:
                return new BookshelfDAOImpl();
            case CATEGORY:
                return new CategoryDAOImpl();
            case CATEGORY_DETAILS:
                return new CategoryDetailsDAOImpl();
            case FINE:
                return new FineDAOImpl();
            case LOAN:
                return new LoanDAOImpl();
            case MEMBER:
                return new MemberDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PUBLISHER:
                return new PublisherDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case RETURN:
                return new ReturnDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}

