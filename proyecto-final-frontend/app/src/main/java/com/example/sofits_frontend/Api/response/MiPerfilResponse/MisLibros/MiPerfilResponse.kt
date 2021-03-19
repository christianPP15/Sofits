package com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros

data class MiPerfilResponse(
    val content: List<LibrosSubidos>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
)