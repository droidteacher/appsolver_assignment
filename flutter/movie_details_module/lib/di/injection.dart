import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';
import 'package:movie_details_module/di/injection.config.dart';

final GetIt serviceLocator = GetIt.instance;

@injectableInit
Future<void> configureDependencies(String? env) async {
  serviceLocator.init(environment: env);
}