package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

//реализация адаптера
class PostAdapter(
    //слушатель
    private val onLikeListener: (Post) -> Unit,
): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var items: List<Post> = emptyList()
        set(value) {
            field = value
            //элементы которые отрисованы, будут обновляться
            notifyDataSetChanged()
        }
    //создание. ViewHolder будет вызываться когда пришел список в адаптер, view еще нет на экране, нужно их создать

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            /*1 параметр - нет активити, есть recyclerView(parent), из него можно взять context, а из context уже layoutInflater,
            2- сам RecyclerView, 3 - false, т.к RecyclerView сам будет решать, когда добавить*/
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLikeListener
        )


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    //класс принимает на вход view и нужен, чтобы держать на нее ссылку, чтобы использовать для обновления элементов списка
    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeListener: (Post) -> Unit//слушатель
    ) : RecyclerView.ViewHolder(binding.root) {
        //обновляет элемент
        fun bind(post: Post) {
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                like.setOnClickListener {
                    onLikeListener(post)
                }
            }
        }
    }
}