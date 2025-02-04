import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:injectable/injectable.dart';
import 'package:movie_details_module/data/remote/model.dart';


abstract class MovieDatasource {
  Future<MovieDetails> fetchMovieDetails(int movieId);
}

@Injectable(as: MovieDatasource)
class TmdbApi implements MovieDatasource {
  late Dio dio;

  TmdbApi() {
    dio = Dio();
    // dio.options.baseUrl = tmdbUrl;
    // dio.options.headers['Authorization'] = bearerToken;

    dio.options.baseUrl = 'https://api.themoviedb.org/3';
    dio.options.headers['Authorization'] = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNmQ1YWEzNDMyYWQzMjYxMjBlZTE2ZDcxMzRjY2UyOSIsIm5iZiI6MTU5NjU1OTAwOC44ODEsInN1YiI6IjVmMjk4ZWEwODY5ZTc1MDAzNjcwNWM5NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MNqiT2CuO9N7Qh4aMQkh2XbTQYiuKY7CYpMhfpdB6WM';    
    dio.options.headers['Accept'] = 'application/json';
    dio.interceptors
        .add(LogInterceptor(responseBody: true, responseHeader: true));
  }

  String get authToken => dotenv.env['AUTH_TOKEN']!;
  String get tmdbUrl => dotenv.env['BASE_URL']!;
  String get bearerToken => 'Bearer $authToken';

  @override
  Future<MovieDetails> fetchMovieDetails(int movieId) async {
    final response = await dio
        .get('/movie/$movieId');

    return MovieDetails.fromJson(response.data);
  }
}
