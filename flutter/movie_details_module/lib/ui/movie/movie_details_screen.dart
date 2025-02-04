import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'package:movie_details_module/data/remote/movie_datasource.dart';
import 'package:movie_details_module/di/injection.dart';
import 'package:movie_details_module/ui/movie/bloc/movie_details_bloc.dart';
import 'package:movie_details_module/ui/movie/bloc/movie_details_event.dart';
import 'package:movie_details_module/ui/movie/bloc/movie_details_state.dart';

class MovieDetailsScreen extends StatelessWidget {
  const MovieDetailsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => MovieDetailsBloc(
          datasource: serviceLocator<MovieDatasource>(),
          platform: serviceLocator<MethodChannel>()),
      child: const MovieDetailsContent(),
    );
  }
}

class MovieDetailsContent extends StatelessWidget {
  const MovieDetailsContent({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey,
      appBar: AppBar(
        title: const Text('Movie details'),
        actions: const [FavoriteButton(), CloseButton()],
      ),
      body: BlocConsumer<MovieDetailsBloc, MovieDetailsState>(
        listener: (context, state) {
          // do nothing
        },
        buildWhen: (previous, current) => current is! FavoriteCheckResult,
        builder: (BuildContext context, MovieDetailsState state) {
          if (state is HumanReadableMessage) {
            return Hud(message: state);
          } else {
            return SingleChildScrollView(
              child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    if (state is GotMovieDetails) ...[
                      MetaData(
                        label: 'Title: ',
                        value: state.details.title,
                      ),
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 8.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.network(state.details.posterPathUrl),
                          ],
                        ),
                      ),
                      MetaData(
                        label: 'Release date: ',
                        value: state.details.releaseDate.toString(),
                      ),
                      MetaData(
                        label: 'Overview: ',
                        value: state.details.overview,
                      ),
                      MetaData(
                        label: 'Budget: ',
                        value: state.details.budget.toString(),
                      ),
                      MetaData(
                        label: 'Run time: ',
                        value: state.details.runtime.toString(),
                      ),
                      MetaData(
                        label: 'Average vote: ',
                        value: state.details.voteAverage.toString(),
                      )
                    ],
                    if (state is FetchingData) ...[
                      const CircularProgressIndicator(),
                    ]
                  ]),
            );
          }
        },
      ),
    );
  }
}

class MetaData extends StatelessWidget {
  final String label;
  final String? value;
  const MetaData({super.key, required this.label, this.value});

  @override
  Widget build(BuildContext context) {
    var textTheme = Theme.of(context).textTheme;
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: textTheme.titleLarge?.copyWith(color: Colors.white),
          ),
          value != null
              ? Text(
                  value!,
                  style: textTheme.bodyLarge?.copyWith(color: Colors.white),
                )
              : const SizedBox(
                  height: 16.0,
                )
        ],
      ),
    );
  }
}

class Hud extends StatelessWidget {
  final HumanReadableMessage message;

  const Hud({super.key, required this.message});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          if (message.isProcessing) ...[
            const CircularProgressIndicator(),
          ],
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 24),
            child: Text(
              message.message,
              style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                  color: message.isError ? Colors.red : Colors.black),
            ),
          )
        ],
      ),
    );
  }
}

class FavoriteButton extends StatelessWidget {
  const FavoriteButton({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<MovieDetailsBloc, MovieDetailsState>(
      builder: (context, state) {
        IconData? iconData;

        if (state is GotMovieDetails) {
          iconData = state.initialFavoriteState
              ? Icons.favorite
              : Icons.favorite_border;
        } else if (state is FavoriteCheckResult) {
          iconData = state.selected ? Icons.favorite : Icons.favorite_border;
        }

        if (iconData != null) {
          return IconButton(
            onPressed: () {
              context.read<MovieDetailsBloc>().add(ToggleFavorite());
            },
            icon: Icon(iconData),
            color: Colors.red,
          );
        } else {
          return const SizedBox.shrink();
        }
      },
    );
  }
}

class CloseButton extends StatelessWidget {
  const CloseButton({super.key});

  @override
  Widget build(BuildContext context) {
    return IconButton(
        onPressed: () {
          if (Platform.isAndroid) {
            SystemNavigator.pop();
          }
        },
        icon: const Icon(Icons.cancel));
  }
}
