class MovieDetails {
  bool adult;
  String? backdropPath;
  int budget;
  List<Genre> genres;
  String homepage;
  int id;
  String imdbId;
  // List<String> originCountry;
  String originalLanguage;
  String originalTitle;
  String overview;
  double popularity;
  String? posterPath;
  List<ProductionCompany> productionCompanies;
  List<ProductionCountry> productionCountries;
  DateTime releaseDate;
  int revenue;
  int runtime;
  List<SpokenLanguage> spokenLanguages;
  String status;
  String tagline;
  String title;
  double voteAverage;
  int voteCount;

  MovieDetails({
    required this.adult,
    this.backdropPath,
    required this.budget,
    required this.genres,
    required this.homepage,
    required this.id,
    required this.imdbId,
    // required this.originCountry,
    required this.originalLanguage,
    required this.originalTitle,
    required this.overview,
    required this.popularity,
    this.posterPath,
    required this.productionCompanies,
    required this.productionCountries,
    required this.releaseDate,
    required this.revenue,
    required this.runtime,
    required this.spokenLanguages,
    required this.status,
    required this.tagline,
    required this.title,
    required this.voteAverage,
    required this.voteCount,
  });

  factory MovieDetails.fromJson(Map data) {
    return MovieDetails(
      adult: data['adult'], 
      backdropPath: data['backdrop_path'], 
      budget: data['budget'], 
      genres: (data['genres'] as List).map((e) => Genre.fromJson(e)).toList(), 
      homepage: data['homepage'], 
      id: data['id'], 
      imdbId: data['imdb_id'], 
      // originCountry: data['origin_country'], 
      originalLanguage: data['original_language'], 
      originalTitle: data['original_title'], 
      overview: data['overview'], 
      popularity: data['popularity'], 
      posterPath: data['poster_path'], 
      productionCompanies: (data['production_companies'] as List).map((e) => ProductionCompany.fromJson(e)).toList(), 
      productionCountries: (data['production_countries'] as List).map((e) => ProductionCountry.fromJson(e)).toList(), 
      releaseDate: DateTime.parse(data['release_date']), 
      revenue: data['revenue'], 
      runtime: data['runtime'], 
      spokenLanguages: (data['spoken_languages'] as List).map((e) => SpokenLanguage.fromJson(e)).toList(), 
      status: data['status'], 
      tagline: data['tagline'], 
      title: data['title'], 
      voteAverage: data['vote_average'], 
      voteCount: data['vote_count']);
  }

  static const posterPathBaseUrl = 'https://image.tmdb.org/t/p/w185';

  String get posterPathUrl => '$posterPathBaseUrl/$posterPath';
}

class Genre {
  int id;
  String name;

  Genre({
    required this.id,
    required this.name,
  });

  factory Genre.fromJson(Map data) {
    return Genre(
      id: data['id'],
      name: data['name'],
    );
  }
}

class ProductionCompany {
  int id;
  String? logoPath;
  String name;
  String originCountry;

  ProductionCompany({
    required this.id,
    this.logoPath,
    required this.name,
    required this.originCountry,
  });

  factory ProductionCompany.fromJson(Map data) {
    return ProductionCompany(
        id: data['id'],
        logoPath: data['logo_path'],
        name: data['name'],
        originCountry: data['origin_country']);
  }
}

class ProductionCountry {
  String iso31661;
  String name;

  ProductionCountry({
    required this.iso31661,
    required this.name,
  });

  factory ProductionCountry.fromJson(Map data) {
    return ProductionCountry(iso31661: data['iso_3166_1'], name: data['name']);
  }
}

class SpokenLanguage {
  String englishName;
  String iso6391;
  String name;

  SpokenLanguage({
    required this.englishName,
    required this.iso6391,
    required this.name,
  });

  factory SpokenLanguage.fromJson(Map data) {
    return SpokenLanguage(
        englishName: data['english_name'],
        iso6391: data['iso_639_1'],
        name: data['name']);
  }
}
