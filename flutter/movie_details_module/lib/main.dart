import 'package:flutter/material.dart';
import 'package:movie_details_module/di/injection.dart';
import 'package:movie_details_module/ui/movie/movie_details_screen.dart';


@pragma("vm:entry-point")
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // await dotenv.load(fileName: '.env');
  configureDependencies(null);
  runApp(const MovieDetailsFlutterModule());
}

class MovieDetailsFlutterModule extends StatelessWidget {
  const MovieDetailsFlutterModule({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Movie details',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      debugShowCheckedModeBanner: false,
      home: const MovieDetailsScreen(),
    );
  }
}

