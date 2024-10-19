package com.example.retrofitget1.model

import com.google.gson.annotations.SerializedName

data class Pockemons(

	@field:SerializedName("Pockemons")
	val pockemons: List<PockemonsItem?>? = null
)

data class TalentsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tc")
	val tc: Boolean? = null
)

data class TypesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class MegaItem(

	@field:SerializedName("orbe")
	val orbe: String? = null,

	@field:SerializedName("sprites")
	val sprites: Sprites? = null
)

data class Evolution(

    @field:SerializedName("next")
	val next: List<NextItem?>? = null,

    @field:SerializedName("mega")
	val mega: Any? = null,

    @field:SerializedName("pre")
	val pre: Any? = null
)

data class Name(

	@field:SerializedName("jp")
	val jp: String? = null,

	@field:SerializedName("en")
	val en: String? = null,

	@field:SerializedName("fr")
	val fr: String? = null
)

data class NextItem(

	@field:SerializedName("pokedex_id")
	val pokedexId: Int? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class ResistancesItem(

	@field:SerializedName("multiplier")
	val multiplier: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class FormesItem(

    @field:SerializedName("name")
	val name: Name? = null,

    @field:SerializedName("region")
	val region: String? = null
)

data class Gmax(

	@field:SerializedName("shiny")
	val shiny: String? = null,

	@field:SerializedName("regular")
	val regular: String? = null
)

data class PockemonsItem(

    @field:SerializedName("generation")
	val generation: Int? = null,

    @field:SerializedName("types")
	val types: Any? = null,

    @field:SerializedName("egg_groups")
	val eggGroups: Any? = null,

    @field:SerializedName("resistances")
	val resistances: Any? = null,

    @field:SerializedName("weight")
	val weight: Any? = null,

    @field:SerializedName("sexe")
	val sexe: Any? = null,

    @field:SerializedName("catch_rate")
	val catchRate: Any? = null,

    @field:SerializedName("evolution")
	val evolution: Any? = null,

    @field:SerializedName("sprites")
	val sprites: Sprites? = null,

    @field:SerializedName("formes")
	val formes: Any? = null,

    @field:SerializedName("pokedex_id")
	val pokedexId: Int? = null,

    @field:SerializedName("stats")
	val stats: Any? = null,

    @field:SerializedName("name")
	val name: Name? = null,

    @field:SerializedName("level_100")
	val level100: Any? = null,

    @field:SerializedName("category")
	val category: String? = null,

    @field:SerializedName("talents")
	val talents: Any? = null,

    @field:SerializedName("height")
	val height: Any? = null,

    @field:SerializedName("next")
	val next: Any? = null
)

data class Sexe(

	@field:SerializedName("female")
	val female: Any? = null,

	@field:SerializedName("male")
	val male: Any? = null
)

data class Stats(

	@field:SerializedName("vit")
	val vit: Int? = null,

	@field:SerializedName("def")
	val def: Int? = null,

	@field:SerializedName("spe_atk")
	val speAtk: Int? = null,

	@field:SerializedName("hp")
	val hp: Int? = null,

	@field:SerializedName("atk")
	val atk: Int? = null,

	@field:SerializedName("spe_def")
	val speDef: Int? = null
)

data class PreItem(

	@field:SerializedName("pokedex_id")
	val pokedexId: Int? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("conditio")
	val conditio: String? = null
)

data class Sprites(

	@field:SerializedName("shiny")
	val shiny: Any? = null,

	@field:SerializedName("gmax")
	val gmax: Any? = null,

	@field:SerializedName("regular")
	val regular: String? = null
)
