package eth.facundoaramayo.simpleprofileview.ui.adapter

import eth.facundoaramayo.simpleprofileview.domain.model.UserModel

interface OnUserItemClickListener {
    fun onItemClicked(userModel: UserModel)
    fun onEditClicked(userModel: UserModel)
    fun onRemoveClicked(userModel: UserModel)
}
