package net.dv.tax.enum.employee

//연봉 세후, 세전
enum class Anual {

    Anual_1("1", "세전"),
    Anual_2("2", "세후");

    var anualType: String
    var anualName: String

    constructor( anualType: String ,anualName: String ) {
        this.anualType = anualType
        this.anualName = anualName
    }
}

fun getAnualName(anualType: String): String{
    var anualName = "";

    Anual.values().filter {
        it.anualType.equals(anualType)
    }?.also{
        if ( it.size > 0 ) anualName = it.get(0).anualName
    }
    return anualName
}







