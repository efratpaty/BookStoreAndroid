package StoreJavaClass;

/**
 * Created by aviga_000 on 09/11/2015.
 */
public enum Subject
{
    SCI_FI,FANTASY,FICTION,NON_FICTION,HISTORICAL,SCIENCE,PSYCHOLOGY,NATURE,ROMANCE,THRILLER, BUSINESS,COOKING,POETRY,TRAVEL,SPIRITUAL, PHILOSOPHY,ANIMALS,KIDS,TEENS,
    YOUNG_ADULT,PREGNANCY,MOTHERHOOD,DIET,LIFE_STYLE, ENCYCLOPEDIA,COMIC_BOOKS,ALL_SUBJECTS;
    private static Subject[] allValues = values();
    public static Subject fromOrdinal(int n) {return allValues[n];}

}
