package com.selpar.selparbulut.data

class SubcatPojo {
    var catname: String? = null
    var catid: String? = null
    var catImage: String? = null

    constructor() {}
    constructor(catid: String?, catname: String?, catimage: String?) {
        this.catid = catid
        this.catname = catname
        catImage = catImage
    }
}