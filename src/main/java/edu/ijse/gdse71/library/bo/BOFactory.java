package edu.ijse.gdse71.library.bo;

import edu.ijse.gdse71.library.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)
                ?boFactory= new BOFactory()
                :boFactory;

    }

    public enum BOTypes{
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

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case AUTHOR:
                return new AuthorBOImpl();
            case AUTHOR_DETAILS:
                return new AuthorDetailsBOImpl();
            case BOOK:
                return new BookBOImpl();
            case BOOKSHELF:
                return new BookshelfBOImpl();
            case CATEGORY:
                return new CategoryBOImpl();
            case CATEGORY_DETAILS:
                return new CategoryDetailsBOImpl();
            case FINE:
                return new FineBOImpl();
            case LOAN:
                return new LoanBOImpl();
            case MEMBER:
                return new MemberBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PUBLISHER:
                return new PublisherBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case RETURN:
                return new ReturnBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}


